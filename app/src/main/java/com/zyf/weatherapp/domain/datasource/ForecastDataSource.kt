package com.zyf.weatherapp.domain.datasource

import com.zyf.weatherapp.domain.model.Forecast
import com.zyf.weatherapp.domain.model.ForecastList

/**
 * Created by zyf on 2018/8/23.
 */
interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?
}