package com.obilet.task.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obilet.task.R
import com.obilet.task.adapter.Travels
import com.obilet.task.databinding.FragmentTravelsBinding
import com.obilet.task.utilities.DashboardProgressListener
import com.obilet.task.viewmodel.FragmentTravelsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentTravels : Fragment() , DashboardProgressListener {

    private lateinit var dashboardProgressListener: DashboardProgressListener
    private lateinit var binding: FragmentTravelsBinding
    private val travels= Travels(arrayListOf())
    private lateinit var viewModel: FragmentTravelsViewModel


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
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTravels)
        recyclerView.layoutManager= LinearLayoutManager(context)
        recyclerView.adapter= travels
        viewModel= ViewModelProvider(this)[FragmentTravelsViewModel::class.java]
        viewModel.getTravelFromAPI()
        arguments?.let {
            val dest= FragmentTravelsArgs.fromBundle(it).destinationName
            val originName= FragmentTravelsArgs.fromBundle(it).originName
            val dateTime= FragmentTravelsArgs.fromBundle(it).dateTime
            binding.destinationRoute.text=originName+"-"+dest
            binding.journeyDate.text=dateTime
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.travelList.observe(viewLifecycleOwner, Observer{
            it?.let {
                travels.updateQueryList(it)
                hideProgressBar()
            }

        })
    }

    override fun showProgressBar() {
        dashboardProgressListener.showProgressBar()
    }

    override fun hideProgressBar() {
        dashboardProgressListener.hideProgressBar()
    }


}