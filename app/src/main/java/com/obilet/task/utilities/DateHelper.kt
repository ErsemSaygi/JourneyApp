package com.obilet.task.utilities

import java.util.*
import javax.inject.Inject

/**
 * Class for the use of Today's Date
 */
class DateHelper @Inject constructor() {

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
}