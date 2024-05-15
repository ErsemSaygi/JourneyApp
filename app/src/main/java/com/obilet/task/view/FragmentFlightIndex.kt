package com.obilet.task.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.obilet.task.databinding.FragmentFlightIndexBinding
import com.obilet.task.model.Location
import com.obilet.task.utilities.DateHelper
import com.obilet.task.view.utils.BaseFragment
import com.obilet.task.view.utils.PassengerTypeSheetFragment
import com.obilet.task.viewmodel.FragmentBusIndexViewModel
import com.obilet.task.viewmodel.FragmentFlightIndexViewModel
import dagger.hilt.android.AndroidEntryPoint
import observe
import javax.inject.Inject
@AndroidEntryPoint
class FragmentFlightIndex : BaseFragment() {
    @Inject
    lateinit var dateHelper: DateHelper
    private lateinit var binding: FragmentFlightIndexBinding
    private val viewModel: FragmentFlightIndexViewModel by activityViewModels()
    private val viewModelBus: FragmentBusIndexViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentFlightIndexBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun observeViewModel() {
        observe(viewModel.passengerType,::getPassengerType)
    }

    private fun getPassengerType(type:String){
        binding.passengerType.text=type
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSession(viewModelBus.busLocations.value)
        startingSetupDate()
        startingSetupTravelText()

        binding.swapTravelCityButton.setOnClickListener{

            val org= binding.originText.text
            binding.originText.text=binding.destinationText.text
            binding.destinationText.text=org
        }


        binding.passengerTypes.setOnClickListener{
            val bottomSheetFragment = PassengerTypeSheetFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, bottomSheetFragment.tag)
        }
    }


    //Setting the date when the program is logged in, using the local database if the user has logged in before

    private fun startingSetupDate(){



            val output=dateHelper.getTomorrow()
            val date=dateHelper.flightIndexDate(output)
            val dateDay=dateHelper.flightIndexDateDay(date)
            val dateMounth=dateHelper.flightIndexDateMounth(date)
            val dateDayName=dateHelper.flightIndexDateDayName(date)

            binding.dateDayReturnText.text=dateDay
            binding.dateMounthReturnText.text = dateMounth.substring(0, 1).uppercase() +
                                                dateMounth.substring(1).lowercase()

            binding.dateDayNameReturnText.text=dateDayName.substring(0, 1).uppercase() +
                                               dateDayName.substring(1).lowercase()




    }


    //Setting the program to destination and arrival cities when logging in,
    // using the local database if the user has logged in before
    private fun startingSetupTravelText(){

            val locationOr=viewModel.busLocations.value?.get(0) as Location
            val locationDes=viewModel.busLocations.value?.get(1) as Location

            binding.destinationText.text=locationDes.name


            binding.originText.text=locationOr.name



    }


}
