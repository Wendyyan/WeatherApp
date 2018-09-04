package com.zyf.weatherapp.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zyf.weatherapp.R
import com.zyf.weatherapp.domain.command.RequestDayForecastCommand
import com.zyf.weatherapp.domain.model.Forecast
import com.zyf.weatherapp.extensions.color
import com.zyf.weatherapp.extensions.textColor
import com.zyf.weatherapp.extensions.toDateString
import com.zyf.weatherapp.ui.view.StarBar
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.*
import java.text.DateFormat

class DetailActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object {
        const val ID = "DetailActivity:id"
        const val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()
        title = intent.getStringExtra(CITY_NAME)
        enableHomeAsUp { onBackPressed() }

        starBar.integerMark = true
        starBar.onStarChangeListener = {
            toast("评价分数：${starBar.starMark.toInt()}")
        }

        doAsync {
            val result = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
            uiThread {
                bindForecast(result)
            }
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast){
        Glide.with(ctx).load(iconUrl).into(ivIcon)
        toolbar.subtitle = date.toDateString(DateFormat.FULL)
        tvDescription.text = description
        bindWeather(high to tvMaxTemperature, low to tvMinTemperature)
    }

    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}"
        it.second.textColor = color(when (it.first) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })
    }
}
