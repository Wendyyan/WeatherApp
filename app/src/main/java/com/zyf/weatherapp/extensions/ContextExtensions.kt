package com.zyf.weatherapp.extensions

import android.content.Context
import android.support.v4.content.ContextCompat

/**
 * Created by zyf on 2018/8/24.
 */
fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)