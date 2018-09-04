package com.zyf.weatherapp.data.server

import com.zyf.weatherapp.data.db.ForecastDb
import com.zyf.weatherapp.domain.datasource.ForecastDataSource
import com.zyf.weatherapp.domain.model.Forecast
import com.zyf.weatherapp.domain.model.ForecastList

/**
 * Created by zyf on 2018/8/23.
 */
class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }

    override fun requestDayForecast(id: Long): Forecast? = throw UnsupportedOperationException()
}