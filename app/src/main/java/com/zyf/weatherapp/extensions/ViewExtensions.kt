package com.zyf.weatherapp.extensions

import android.content.Context
import android.view.View
import android.widget.TextView
import kotlin.properties.ReadWriteProperty

/**
 * Created by zyf on 2018/8/22.
 */
val View.ctx: Context
    get() = context

var TextView.textColor: Int
    get() = currentTextColor
    set(value) = setTextColor(value)

fun View.slideExit(){
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

fun View.slideEnter(){
    if (translationY < 0f) animate().translationY(0f)
}