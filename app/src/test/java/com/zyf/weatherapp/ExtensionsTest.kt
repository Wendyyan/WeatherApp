package com.zyf.weatherapp

import com.zyf.weatherapp.extensions.toDateString
import org.junit.Test

import org.junit.Assert.*
import java.text.DateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExtensionsTest {
    @Test
    fun testLongToDateString() {
        assertEquals("2015-10-20", 1445275635000L.toDateString())
    }

    @Test
    fun testDateStringFullFormat(){
        assertEquals("2015年10月20日 星期二", 1445275635000L.toDateString(DateFormat.FULL))
    }
}