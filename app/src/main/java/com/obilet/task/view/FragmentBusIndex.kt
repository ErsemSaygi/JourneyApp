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
import com.obilet.task.view.utils.BaseFragment
import com.obilet.task.view.utils.DashBoardFragmentDirections
import com.obilet.task.view.utils.DatePickerFragment
import com.obilet.task.viewmodel.FragmentBusIndexViewModel
import dagger.hilt.android.AndroidEntryPoint
import observe
import javax.inject.Inject

@AndroidEntryPoint
class FragmentBusIndex : BaseFragment() {
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


        if(viewModel.busLocations.value?.isNotEmpty() == true)
        {
            hideProgressBar()}
        else{
            viewModel.refreshFromAPI()
        }

        //City selection for departure
        binding.originLinearLayout.setOnClickListener {navigateTravelQuery(ButtonType.ORIGIN) }

        //City selection for arrival
        binding.destinationLinearLayout.setOnClickListener {navigateTravelQuery(ButtonType.DESTINATION)}

        //For date selection button
        binding.dateIcon.setOnClickListener {pickDate()}

        //To change within the cities themselves
        binding.swapTravelCityButton.setOnClickListener{swapTravelCity()}

        //For today button
        binding.buttonToday.setOnClickListener{todayButton()}

        //For tomorrow button
        binding.buttonTomorrow.setOnClickListener{tomorrowButton()}

        //To find travels
        binding.buttonFindTickets.setOnClickListener{findTickets()}
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

    override fun observeViewModel() {
        observe(viewModel.progressLoading, ::loading)
        observe(viewModel.date, ::date)
        observe(viewModel.btnType, ::buttnType)
    }


    private fun loading(status:Boolean){

        status.let{
            if(!status)
                hideProgressBar()
            else
                showProgressBar()
            startingSetupTravelText()
        }

    }

    private fun date(date:String){
        date.let{

            binding.dateText.text=it
        }
    }
    private fun navigateTravelQuery(buttonType: ButtonType){
        val action = DashBoardFragmentDirections.actionDashBoardFragmentToFragmentTravelQuery(buttonType)
        findNavController().navigate(action)
    }
    private fun buttnType(btn:ButtonType){
        btn.let{
            val location=viewModel.findCity(viewModel.cityId.value.toString())
            if(btn== ButtonType.ORIGIN){
                binding.destinationText.text= viewModel.destinationName
                viewModel.originName=location.name
                binding.originText.text=location.name
                viewModel.originID=viewModel.cityId.value!!.toInt()
            }
            else if(it==ButtonType.DESTINATION) {
                binding.originText.text= viewModel.originName
                viewModel.destinationName=location.name
                binding.destinationText.text=location.name
                viewModel.destinationID=viewModel.cityId.value!!.toInt()
            }

        }
    }

    private fun pickDate(){
        val datePicker = DatePickerFragment()
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun swapTravelCity(){
        viewModel.swapTravelCity()
        val org= binding.originText.text
        binding.originText.text=binding.destinationText.text
        binding.destinationText.text=org
    }

    private fun todayButton(){
        val output=dateHelper.getDate()
        val date=dateHelper.presentationDate(output)
        viewModel.saveLocalDate(output)
        viewModel.date.value=date
    }

    private fun tomorrowButton(){
        val output=dateHelper.getTomorrow()
        val date=dateHelper.presentationDate(output)
        viewModel.saveLocalDate(output)
        viewModel.date.value=date
    }

    private fun findTickets(){
        val beforeDate=viewModel.compareDate(binding.dateText.text.toString())

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

        viewModel.dateTravel=dateS.toString()
        if(dateS==""){
            val output=dateHelper.getTomorrow()
            val date=dateHelper.presentationDate(output)
            viewModel.dateTravel=output
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
            viewModel.originID=orgn.id
        }
        else{
            val locationOr=viewModel.busLocations.value?.get(0) as Location
            binding.originText.text=locationOr.name
            viewModel.originID=locationOr.id
        }

        if(destinationId!=""){

            val destn=viewModel.findCity(destinationId!!) as Location
            viewModel.destinationName=destn.name
            binding.destinationText.text=destn.name
            viewModel.destinationID=destn.id
        }

        else{

            val locationDes=viewModel.busLocations.value?.get(1) as Location
            binding.destinationText.text=locationDes.name
            viewModel.destinationID=locationDes.id


        }

    }


}
