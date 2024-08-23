package com.nutrilog.app

import com.nutrilog.app.utils.helpers.getDaysInMonth
import org.junit.Assert.assertEquals
import org.junit.Test

class DateTest {
    @Test
    fun `given dates in month`() {
        val year = 2024
        val month = 4

        val daysInMay2024 = getDaysInMonth(month, year)
        assertEquals(31, daysInMay2024.size)

        assertEquals("Wednesday", daysInMay2024[0].dayOfWeek)
        assertEquals(1, daysInMay2024[0].dayOfMonth)

        assertEquals("Friday", daysInMay2024[30].dayOfWeek)
        assertEquals(31, daysInMay2024[30].dayOfMonth)
    }
}
