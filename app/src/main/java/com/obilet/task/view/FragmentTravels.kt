package com.obilet.task.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obilet.task.R
import com.obilet.task.adapter.Travels
import com.obilet.task.databinding.FragmentTravelsBinding
import com.obilet.task.model.JourneyInfo
import com.obilet.task.utilities.ButtonType
import com.obilet.task.utilities.DashboardProgressListener
import com.obilet.task.view.utils.BaseFragment
import com.obilet.task.view.utils.DashBoardFragmentDirections
import com.obilet.task.viewmodel.FragmentBusIndexViewModel
import com.obilet.task.viewmodel.FragmentTravelsViewModel
import dagger.hilt.android.AndroidEntryPoint
import observe

@AndroidEntryPoint
class FragmentTravels : BaseFragment() , DashboardProgressListener {

    private lateinit var dashboardProgressListener: DashboardProgressListener
    private lateinit var binding: FragmentTravelsBinding
    private val travels= Travels(arrayListOf())
    private lateinit var viewModel: FragmentTravelsViewModel
    private val viewModelBus: FragmentBusIndexViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DashboardProgressListener) {
            dashboardProgressListener = context
        } else {
            throw RuntimeException("$context must implement DashboardProgressListener")
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentTravelsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel= ViewModelProvider(this)[FragmentTravelsViewModel::class.java]
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTravels)
        recyclerView.layoutManager= LinearLayoutManager(context)
        recyclerView.adapter= travels
        viewModel.getTravelFromAPI(viewModelBus.originID,viewModelBus.sessionId,viewModelBus.destinationID,viewModelBus.deviceID,viewModelBus.dateTravel)
        arguments?.let {
            val dest= FragmentTravelsArgs.fromBundle(it).destinationName
            val originName= FragmentTravelsArgs.fromBundle(it).originName
            val dateTime= FragmentTravelsArgs.fromBundle(it).dateTime
            binding.destinationRoute.text=originName+"-"+dest
            binding.journeyDate.text=dateTime
        }

        binding.backButton.setOnClickListener{navigateHome()}

    }

    override fun observeViewModel() {
        observe(viewModel.travelList ,:: getTravelList)
    }

    private fun getTravelList(list:List<JourneyInfo>){
        travels.updateQueryList(list)
        hideProgressBar()
    }

    private fun navigateHome(){
        val action = FragmentTravelsDirections.actionFragmentTravelsToDashBoardFragment(ButtonType.DEFAULT,"")

        findNavController().navigate(action)
    }
    override fun showProgressBar() {
        dashboardProgressListener.showProgressBar()
    }

    override fun hideProgressBar() {
        dashboardProgressListener.hideProgressBar()
    }


}