package com.obilet.task.view.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.obilet.task.R
import com.obilet.task.viewmodel.FragmentFlightIndexViewModel

class PassengerTypeSheetFragment : BottomSheetDialogFragment() {

    private lateinit var rootView: View
    private lateinit var passengerTypeListView: ListView

    private val viewModel: FragmentFlightIndexViewModel by activityViewModels()

    private val passengerTypes = listOf("Yetişkin", "Çocuk", "Bebek")
    private val passengerCounts = mutableListOf(1, 0, 0) // default counts: 1 adult, 0 child, 0 baby

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_passenger_type_sheet, container, false)
        passengerTypeListView = rootView.findViewById(R.id.passengerTypeListView)

        val adapter = PassengerTypeAdapter()
        passengerTypeListView.adapter = adapter

        return rootView
    }

    private inner class PassengerTypeAdapter : android.widget.BaseAdapter() {

        override fun getCount(): Int {
            return passengerTypes.size
        }

        override fun getItem(position: Int): Any {
            return passengerTypes[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.item_passengertype, parent, false)

            val passengerTypeTextView: TextView = view.findViewById(R.id.passengerTypeTextView)
            val numberTextView: TextView = view.findViewById(R.id.numberTextView)
            val plusButton: Button = view.findViewById(R.id.plusButton)
            val minusButton: Button = view.findViewById(R.id.minusButton)

            passengerTypeTextView.text = passengerTypes[position]
            numberTextView.text = passengerCounts[position].toString()

            plusButton.setOnClickListener { increaseNumber(position, numberTextView) }
            minusButton.setOnClickListener { decreaseNumber(position, numberTextView) }

            return view
        }
    }

    private fun increaseNumber(position: Int, textView: TextView) {
        var number = textView.text.toString().toInt()
        number++
        textView.text = number.toString()
        passengerCounts[position] = number
        getPassengerTypeText()
    }

    private fun decreaseNumber(position: Int, textView: TextView) {
        var number = textView.text.toString().toInt()
        if (position == 0 && number == 1) {
            // Do not allow less than 1 adult
            print("1 kişiden az olamaz")
        } else if (number > 0) {
            number--
            textView.text = number.toString()
            passengerCounts[position] = number
        }
        getPassengerTypeText()
    }

    private fun getPassengerTypeText() {
        val stringBuilder = StringBuilder()
        val adultNumber = passengerCounts[0]
        val childNumber = passengerCounts[1]
        val babyNumber = passengerCounts[2]

        if (adultNumber > 0) stringBuilder.append("$adultNumber YETİŞKİN ")
        if (childNumber > 0) stringBuilder.append("$childNumber ÇOCUK ")
        if (babyNumber > 0) stringBuilder.append("$babyNumber BEBEK")

        viewModel.passengerType.value = stringBuilder.toString()
    }
}
