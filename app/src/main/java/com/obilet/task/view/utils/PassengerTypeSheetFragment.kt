package com.obilet.task.view.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.obilet.task.R


class PassengerTypeSheetFragment : BottomSheetDialogFragment() {

    private lateinit var rootView: View
    private lateinit var plusButton1: Button
    private lateinit var minusButton1: Button
    private lateinit var numberTextView1: TextView
    private lateinit var plusButton2: Button
    private lateinit var minusButton2: Button
    private lateinit var numberTextView2: TextView
    private lateinit var plusButton3: Button
    private lateinit var minusButton3: Button
    private lateinit var numberTextView3: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_passenger_type_sheet, container, false)


        plusButton1 = rootView.findViewById(R.id.plusButton1)
        minusButton1 = rootView.findViewById(R.id.minusButton1)
        numberTextView1 = rootView.findViewById(R.id.numberTextView1)
        plusButton2 = rootView.findViewById(R.id.plusButton2)
        minusButton2 = rootView.findViewById(R.id.minusButton2)
        numberTextView2 = rootView.findViewById(R.id.numberTextView2)
        plusButton3 = rootView.findViewById(R.id.plusButton3)
        minusButton3 = rootView.findViewById(R.id.minusButton3)
        numberTextView3 = rootView.findViewById(R.id.numberTextView3)


        plusButton1.setOnClickListener { increaseNumber(numberTextView1) }
        minusButton1.setOnClickListener { decreaseNumber(numberTextView1) }
        plusButton2.setOnClickListener { increaseNumber(numberTextView2) }
        minusButton2.setOnClickListener { decreaseNumber(numberTextView2) }
        plusButton3.setOnClickListener { increaseNumber(numberTextView3) }
        minusButton3.setOnClickListener { decreaseNumber(numberTextView3) }

        return rootView
    }
    //Passenger number increase
    private fun increaseNumber(textView: TextView) {
        var number = textView.text.toString().toInt()
        number++
        textView.text = number.toString()
    }

    //Passenger number increase
    private fun decreaseNumber(textView: TextView) {
        var number = textView.text.toString().toInt()
        if (number > 0) {
            number--
            textView.text = number.toString()
        }
    }
}
