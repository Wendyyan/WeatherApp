package com.zyf.weatherapp.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.zyf.weatherapp.ui.App
import org.jetbrains.anko.db.*

/**
 * Created by zyf on 2018/8/23.
 */
class ForecastDbHelper(ctx: Context = App.instance) : ManagedSQLiteOpenHelper(
        App.instance, ForecastDbHelper.DB_NAME, null, ForecastDbHelper.DB_VERSION) {

    companion object {
        const val DB_NAME = "forecast.db"
        const val DB_VERSION = 1
        val instance by lazy { ForecastDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(CityForecastTable.NAME, true,
                Pair(CityForecastTable.ID, INTEGER + PRIMARY_KEY),
                Pair(CityForecastTable.CITY, TEXT),
                Pair(CityForecastTable.COUNTRY, TEXT))

        db.createTable(DayForecastTable.NAME, true,
                Pair(DayForecastTable.ID, INTEGER + PRIMARY_KEY + AUTOINCREMENT),
                Pair(DayForecastTable.DATE, INTEGER),
                Pair(DayForecastTable.DESCRIPTION, TEXT),
                Pair(DayForecastTable.HIGH, INTEGER),
                Pair(DayForecastTable.LOW, INTEGER),
                Pair(DayForecastTable.ICON_URL, TEXT),
                Pair(DayForecastTable.CITY_ID, INTEGER))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(CityForecastTable.NAME, true)
        db.dropTable(DayForecastTable.NAME, true)
        onCreate(db)
    }
}