package com.zyf.weatherapp.ui

import android.app.Application
import com.zyf.weatherapp.extensions.DelegateExt

/**
 * Created by zyf on 2018/8/22.
 */
class App: Application() {

    companion object {
        var instance: App by DelegateExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}