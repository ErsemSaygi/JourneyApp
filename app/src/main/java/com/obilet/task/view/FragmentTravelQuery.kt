package com.obilet.task.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.obilet.task.adapter.LocationQuery
import com.obilet.task.databinding.FragmentTravelQueryBinding
import com.obilet.task.model.Location
import com.obilet.task.utilities.ButtonType
import com.obilet.task.view.utils.BaseFragment
import com.obilet.task.viewmodel.FragmentBusIndexViewModel
import com.obilet.task.viewmodel.FragmentTravelQueryViewModel
import dagger.hilt.android.AndroidEntryPoint
import observe

@AndroidEntryPoint
class FragmentTravelQuery : BaseFragment() {
    private val locationQueryAdapter= LocationQuery(arrayListOf())
    private var buttonType: ButtonType =ButtonType.ORIGIN
    private lateinit var binding: FragmentTravelQueryBinding
    private lateinit var viewModel:FragmentTravelQueryViewModel
    private val viewModelBus: FragmentBusIndexViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentTravelQueryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel= ViewModelProvider(this)[FragmentTravelQueryViewModel::class.java]
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.resultQueryRecyclerView)
        recyclerView.layoutManager= LinearLayoutManager(context)
        recyclerView.adapter= locationQueryAdapter

        arguments?.let {
            buttonType= FragmentTravelQueryArgs.fromBundle(it).buttonType
        }
        binding.closeButton?.setOnClickListener {
            findNavController().navigateUp()
        }

        //If the number of char that the user wants to search exceeds 2, the search process will be automatic.
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val inputLength = s?.length ?: 0
                if (inputLength > 2) {
                    viewModel.searchCity(s.toString(),viewModelBus.sessionId,viewModelBus.deviceID)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


    }

    override fun observeViewModel() {
        observe(viewModel.busLocationsQuery,:: busLocationQuery)

    }


    private fun busLocationQuery(list:List<Location>){
        locationQueryAdapter.updateQueryList(list,buttonType)

    }







}