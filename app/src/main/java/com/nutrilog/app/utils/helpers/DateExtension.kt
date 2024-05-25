package com.nutrilog.app.utils.helpers

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class DayInfo(val dayOfWeek: String, val dayOfMonth: Int)

/**
 * Get calender from date
 *
 * @param locale locale
 * @return triple of day, month, year
 *
 * @see [Triple]
 * Calender day starts from 0
 * Calender month starts from 0
 */
fun Date.getCalender(locale: Locale): Triple<Int, Int, Int> {
    val calender = Calendar.getInstance(locale)
    calender.time = this
    return Triple(
        calender.get(Calendar.DAY_OF_MONTH),
        calender.get(Calendar.MONTH),
        calender.get(Calendar.YEAR),
    )
}

/**
 * Get days in month
 *
 * @param year year
 * @param month month (0-11) on calender month starts from 0
 * @return list of [DayInfo]
 */
fun getDaysInMonth(
    month: Int,
    year: Int,
): List<DayInfo> {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    val daysInMonth = mutableListOf<DayInfo>()

    while (calendar.get(Calendar.MONTH) == month) {
        val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        daysInMonth.add(DayInfo(dayOfWeek, dayOfMonth))
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return daysInMonth
}

/**
 * Get time in millis
 *
 * @param day day of month on calender day starting from 0
 * @param month month (0-11) on calender month starts from 0
 * @param year year
 */
fun getTimeInMillis(
    day: Int,
    month: Int,
    year: Int,
): Long {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    return calendar.timeInMillis
}
