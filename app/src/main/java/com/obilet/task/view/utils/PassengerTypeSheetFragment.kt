package com.obilet.task.view.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.obilet.task.R
import com.obilet.task.databinding.FragmentBusIndexBinding
import com.obilet.task.viewmodel.FragmentBusIndexViewModel
import com.obilet.task.viewmodel.FragmentFlightIndexViewModel


class PassengerTypeSheetFragment : BottomSheetDialogFragment() {

    private lateinit var rootView: View
    private lateinit var adultplusButton1: Button
    private lateinit var adultminusButton1: Button
    private lateinit var adultnumberTextView1: TextView
    private lateinit var childplusButton2: Button
    private lateinit var childminusButton2: Button
    private lateinit var childnumberTextView2: TextView
    private lateinit var babyplusButton3: Button
    private lateinit var babyminusButton3: Button
    private lateinit var babynumberTextView3: TextView

    private val viewModel: FragmentFlightIndexViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_passenger_type_sheet, container, false)


        adultplusButton1 = rootView.findViewById(R.id.plusButton1)
        adultminusButton1 = rootView.findViewById(R.id.minusButton1)
        adultnumberTextView1 = rootView.findViewById(R.id.numberTextView1)
        childplusButton2 = rootView.findViewById(R.id.plusButton2)
        childminusButton2 = rootView.findViewById(R.id.minusButton2)
        childnumberTextView2 = rootView.findViewById(R.id.numberTextView2)
        babyplusButton3 = rootView.findViewById(R.id.plusButton3)
        babyminusButton3 = rootView.findViewById(R.id.minusButton3)
        babynumberTextView3 = rootView.findViewById(R.id.numberTextView3)


        adultplusButton1.setOnClickListener { increaseNumber(adultnumberTextView1) }
        adultminusButton1.setOnClickListener { decreaseNumber(adultnumberTextView1) }
        childplusButton2.setOnClickListener { increaseNumber(childnumberTextView2) }
        childminusButton2.setOnClickListener { decreaseNumber(childnumberTextView2) }
        babyplusButton3.setOnClickListener { increaseNumber(babynumberTextView3) }
        babyminusButton3.setOnClickListener { decreaseNumber(babynumberTextView3) }

        return rootView
    }
    //Passenger number increase
    private fun increaseNumber(textView: TextView) {
        var number = textView.text.toString().toInt()
        number++
        textView.text = number.toString()
        getPassengerTypeText()
    }

    //Passenger number increase
    private fun decreaseNumber(textView: TextView) {
        var number = textView.text.toString().toInt()
        val tag = textView.tag as String
        if(tag=="yetişkin" && number==1){
            print("1 kişiden az olamaz")
        }
        else if (number > 0) {
            number--
            textView.text = number.toString()
        }
        getPassengerTypeText()
    }
    private fun getPassengerTypeText(){
        val stringBuilder = StringBuilder()
       val adultNumber= adultnumberTextView1.text.toString().toInt()
       val childNumber= childnumberTextView2.text.toString().toInt()
       val babyNumber= babynumberTextView3.text.toString().toInt()

        if(adultNumber>0)stringBuilder.append(adultNumber.toString()+" YETİŞKİN ")
        if(childNumber>0)stringBuilder.append(childNumber.toString()+" ÇOCUK ")
        if(babyNumber>0)stringBuilder.append(babyNumber.toString()+" BEBEK")

        viewModel.passengerType.value=stringBuilder.toString()

    }
}
