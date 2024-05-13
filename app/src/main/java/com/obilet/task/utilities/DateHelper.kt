package com.obilet.task.utilities

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Class for the use of Date
 */
class DateHelper @Inject constructor() {
    //Get today date
    fun getDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        return "$year-$month-$dayOfMonth" + "T" + "$hour:$minute:$second"

    }
    //Post date destinationdate
    fun convertTravelDate(date: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val originalDate = originalFormat.parse(date)

        val targetFormat = SimpleDateFormat("yyyy-M-dd", Locale.getDefault())
        val dateString = targetFormat.format(originalDate)

        return dateString.toString()
    }
    //Convert ui date
    fun presentationDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy EEEE", Locale("tr"))

        return outputFormat.format(inputFormat.parse(date)!!)
    }
    //Convert date to "dd MMMM EEEE" format
    fun flightIndexDate(date: String): String {

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM EEEE", Locale("tr", "TR"))

            val date = inputFormat.parse(date)
            return outputFormat.format(date)?.uppercase(Locale("tr")) ?: ""

    }
    //Convert date to "dd" format
    fun flightIndexDateDay(date: String): String {

        val outputFormat = SimpleDateFormat("dd", Locale("tr", "TR"))
        val inputFormat = SimpleDateFormat("dd MMMM EEEE", Locale("tr", "TR"))

        val date = inputFormat.parse(date)
        return outputFormat.format(date)?.uppercase(Locale("tr")) ?: ""

    }
    //Convert date to "MMMM" format
    fun flightIndexDateMounth(date: String): String {

        val outputFormat = SimpleDateFormat("MMMM", Locale("tr", "TR"))
        val inputFormat = SimpleDateFormat("dd MMMM EEEE", Locale("tr", "TR"))

        val date = inputFormat.parse(date)
        return outputFormat.format(date)?.uppercase(Locale("tr")) ?: ""

    }
    //Convert date to "EEEE" format
    fun flightIndexDateDayName(date: String): String {

        val outputFormat = SimpleDateFormat("EEEE", Locale("tr", "TR"))
        val inputFormat = SimpleDateFormat("dd MMMM EEEE", Locale("tr", "TR"))

        val date = inputFormat.parse(date)
        return outputFormat.format(date)?.uppercase(Locale("tr")) ?: ""

    }
    //To get tomorrow's date
    fun getTomorrow(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) + 1
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        return "$year-$month-$dayOfMonth" + "T" + "$hour:$minute:$second"
}}