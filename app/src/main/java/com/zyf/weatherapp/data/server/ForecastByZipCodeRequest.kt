package com.zyf.weatherapp.data.server

import android.util.Log
import com.google.gson.Gson
import java.net.URL

/**
 * Created by zyf on 2018/8/24.
 */
class ForecastByZipCodeRequest(private val zipCode: Long, private val gson: Gson = Gson()) {

    companion object {
        private const val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private const val APP_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"
        private const val COMPLETE_URL = "$APP_URL&APPID=$APP_ID&q="
    }

    fun execute(): ForecastResult {
        val url = COMPLETE_URL + zipCode
        Log.d("URL", url)
        val forecastJsonStr = URL(url).readText()
        return gson.fromJson(forecastJsonStr, ForecastResult::class.java)
    }

}