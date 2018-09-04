package com.zyf.weatherapp.domain.command

import com.zyf.weatherapp.domain.datasource.ForecastProvider
import com.zyf.weatherapp.domain.model.ForecastList

/**
 * Created by zyf on 2018/8/22.
 */
class RequestForecastCommand(
        private val zipCode: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<ForecastList> {

    companion object {
        const val DAYS = 7
    }

    override fun execute() = forecastProvider.requestByZipCode(zipCode, DAYS)
}