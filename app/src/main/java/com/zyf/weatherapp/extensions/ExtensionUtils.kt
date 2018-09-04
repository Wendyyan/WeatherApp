package com.zyf.weatherapp.extensions

import java.text.DateFormat
import java.util.*

/**
 * Created by zyf on 2018/8/24.
 */
fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String{
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}