package com.zyf.weatherapp.domain.datasource

import com.zyf.weatherapp.data.db.ForecastDb
import com.zyf.weatherapp.domain.model.ForecastList
import com.zyf.weatherapp.extensions.firstResult
import com.zyf.weatherapp.data.server.ForecastServer
import com.zyf.weatherapp.domain.model.Forecast

/**
 * Created by zyf on 2018/8/23.
 */
class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        const val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES by lazy { listOf(ForecastDb(), ForecastServer()) }
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSource{
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (res != null && res.size() >= days) res else null
    }

    fun requestForecast(id: Long): Forecast = requestToSource { it.requestDayForecast(id) }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun <T : Any> requestToSource(f: (ForecastDataSource) -> T?): T
            = sources.firstResult { f(it) }
}