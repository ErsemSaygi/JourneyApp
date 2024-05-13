package com.obilet.task.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.obilet.task.databinding.FragmentFlightIndexBinding
import com.obilet.task.view.utils.PassengerTypeSheetFragment

class FragmentFlightIndex : Fragment() {
    private lateinit var binding: FragmentFlightIndexBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentFlightIndexBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.passengerTypes.setOnClickListener{
            val bottomSheetFragment = PassengerTypeSheetFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, bottomSheetFragment.tag)
        }
    }

}
