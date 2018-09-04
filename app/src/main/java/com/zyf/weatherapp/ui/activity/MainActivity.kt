package com.zyf.weatherapp.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.zyf.weatherapp.ui.adapter.ForecastListAdapter
import com.zyf.weatherapp.R
import com.zyf.weatherapp.domain.command.RequestForecastCommand
import com.zyf.weatherapp.example.Icon
import com.zyf.weatherapp.extensions.DelegateExt
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    private val zipCode: Long by DelegateExt.preference(this,
            SettingsActivity.ZIP_CODE, SettingsActivity.DEFAULT_ZIP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)

        val items = listOf("Android", "java")
        for (item in items){
            println(item)
        }
        for (x in 1..10 step 2){
            println(x)
        }

        val x: IntArray = intArrayOf(1, 2, 3)
        x[0] = x[1] + x[2]
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() = doAsync {
        val result = RequestForecastCommand(zipCode).execute()
        uiThread { _ ->
            val adapter = ForecastListAdapter(result){
                startActivity<DetailActivity>(DetailActivity.ID to it.id,
                        DetailActivity.CITY_NAME to result.city)
            }
            forecastList.adapter = adapter
            toolbarTitle = "{${result.city}(${result.country})}"
        }
    }
}
