package com.obilet.task.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.obilet.task.R
import com.obilet.task.databinding.FragmentBusIndexBinding
import com.obilet.task.model.Location
import com.obilet.task.utilities.*
import com.obilet.task.view.utils.DashBoardFragmentDirections
import com.obilet.task.view.utils.DatePickerFragment
import com.obilet.task.viewmodel.FragmentBusIndexViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentBusIndex : Fragment() {
    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var dateHelper: DateHelper

    private lateinit var binding: FragmentBusIndexBinding
    private val viewModel: FragmentBusIndexViewModel by activityViewModels()
    private lateinit var dashboardProgressListener: DashboardProgressListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentBusIndexBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startingSetupDate()
        observeLiveData()

        if(Constants.listLocation.isNotEmpty())
        {
            hideProgressBar()}
        else{
            viewModel.refreshFromAPI()
        }

        //City selection for departure
        binding.originLinearLayout.setOnClickListener {

            val action = DashBoardFragmentDirections.actionDashBoardFragmentToFragmentTravelQuery(ButtonType.ORIGIN)
            val options = NavOptions.Builder()
                .setPopUpTo(R.id.fragmentBusIndex, false)
                .build()


            findNavController().navigate(action,options)
        }

        //City selection for arrival
        binding.destinationLinearLayout.setOnClickListener {

            val action = DashBoardFragmentDirections.actionDashBoardFragmentToFragmentTravelQuery(ButtonType.DESTINATION)


            findNavController().navigate(action)
        }

        //For date selection button
        binding.dateIcon.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.show(childFragmentManager, "datePicker")
        }

        //To change within the cities themselves
        binding.swapTravelCityButton.setOnClickListener{
            viewModel.swapTravelCity()
            val org= binding.originText.text
            binding.originText.text=binding.destinationText.text
            binding.destinationText.text=org
        }

        //For today button
        binding.buttonToday.setOnClickListener{
            val output=dateHelper.getDate()
            val date=dateHelper.presentationDate(output)
            viewModel.saveLocalDate(output)
            viewModel.date.value=date

        }

        //For tomorrow button
        binding.buttonTomorrow.setOnClickListener{
            val output=dateHelper.getTomorrow()
            val date=dateHelper.presentationDate(output)
            viewModel.saveLocalDate(output)
            viewModel.date.value=date
        }

        //To find travels
        binding.buttonFindTickets.setOnClickListener{
            var beforeDate=viewModel.compareDate(binding.dateText.text.toString())

            if(viewModel.originName==viewModel.destinationName){

                showError("Gidiş-varış lokasyonları aynı.")
            }
            else if(beforeDate){
                showError("Tarih önceye ait.")
            }
            else{
                showProgressBar()
                val action = DashBoardFragmentDirections.actionDashBoardFragmentToFragmentTravels(
                    binding.originText.text.toString(),
                    binding.destinationText.text.toString(),
                    binding.dateText.text.toString()
                )


                findNavController().navigate(action)
            }

        }
    }


    //Definition for the connection of the LoadingBar
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DashboardProgressListener) {
            dashboardProgressListener = context
        } else {
            throw RuntimeException("$context must implement DashboardProgressListener")
        }
    }

    //Alert-dialog for some erroneous situations
    private fun showError(message:String){
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.apply {
            setTitle("Uyarı")
            setMessage(message)
            setPositiveButton("Tamam") { dialog, _ ->

                dialog.dismiss()
            }

        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    //Show LoadingBar
    private fun showProgressBar() {
        dashboardProgressListener.showProgressBar()

    }

    //Hide LoadingBar
    private fun hideProgressBar() {
        dashboardProgressListener.hideProgressBar()

    }

    //Setting the date when the program is logged in, using the local database if the user has logged in before
    private fun startingSetupDate(){
        val dateS=sharedPreferencesManager.getString("date","")

        Constants.dateTravel=dateS.toString()
        if(dateS==""){
            val output=dateHelper.getTomorrow()
            val date=dateHelper.presentationDate(output)
            Constants.dateTravel=output
            viewModel.saveLocalDate(output)
            viewModel.date.value=date
        }
        else{
            val date=dateHelper.presentationDate(dateS.toString())
            viewModel.date.value=date
        }
    }


    //Setting the program to destination and arrival cities when logging in,
    // using the local database if the user has logged in before
    private fun startingSetupTravelText(){
        val originId=sharedPreferencesManager.getString("originId","")
        val destinationId=sharedPreferencesManager.getString("destinationId","")

        if(originId!="" ){
            val orgn= viewModel.findCity(originId!!) as Location
            binding.originText.text=orgn.name
            viewModel.originName=orgn.name
            Constants.originId=orgn.id
        }
        else{
            val locationOr=viewModel.busLocations.value?.get(0) as Location
            binding.originText.text=locationOr.name
            Constants.originId=locationOr.id
        }

        if(destinationId!=""){

            val destn=viewModel.findCity(destinationId!!) as Location
            viewModel.destinationName=destn.name
            binding.destinationText.text=destn.name
            Constants.destinationId=destn.id
        }

        else{

            val locationDes=viewModel.busLocations.value?.get(1) as Location
            binding.destinationText.text=locationDes.name
            Constants.destinationId=locationDes.id


        }

    }

    //LiveData
    private fun observeLiveData(){
        viewModel.progressLoading.observe(this,Observer{
            it?.let{
                if(!it)
                    hideProgressBar()
                else
                    showProgressBar()
                startingSetupTravelText()
            }

        })

        viewModel.date.observe(this,Observer{
            it?.let{

                binding.dateText.text=it
            }

        })
        viewModel.btnType.observe(this, Observer{
            it?.let{
                val location=viewModel.findCity(viewModel.cityId.value.toString())
                if(it== ButtonType.ORIGIN){
                    binding.destinationText.text= viewModel.destinationName
                    viewModel.originName=location.name
                    binding.originText.text=location.name
                    Constants.originId=viewModel.cityId.value!!.toInt()
                }
                else if(it==ButtonType.DESTINATION) {
                    binding.originText.text= viewModel.originName
                    viewModel.destinationName=location.name
                    binding.destinationText.text=location.name
                    Constants.destinationId=viewModel.cityId.value!!.toInt()
                }

            }

        })
    }
}
