package com.zyf.weatherapp.domain.command

import com.zyf.weatherapp.domain.datasource.ForecastProvider
import com.zyf.weatherapp.domain.model.Forecast

/**
 * Created by zyf on 2018/8/24.
 */
class RequestDayForecastCommand(
        private val id: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()):
        Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}