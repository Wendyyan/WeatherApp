package com.zyf.weatherapp.ui.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zyf.weatherapp.R
import com.zyf.weatherapp.domain.model.Forecast
import com.zyf.weatherapp.domain.model.ForecastList
import com.zyf.weatherapp.extensions.ctx
import com.zyf.weatherapp.extensions.toDateString
import kotlinx.android.synthetic.main.item_forecast.view.*

/**
 * Created by zyf on 2018/8/22.
 */
class ForecastListAdapter(private val weekForecast: ForecastList,
                          private val itemClick: (Forecast) -> Unit):
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx)
                .inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    override fun getItemCount(): Int = weekForecast.size()

    class ViewHolder(view: View, private val itemClick : (Forecast) -> Unit): RecyclerView.ViewHolder(view){

        fun bindForecast(forecast: Forecast){
            with(forecast){
                Glide.with(itemView.ctx).load(iconUrl).into(itemView.ivIcon)
                itemView.tvDate.text = date.toDateString()
                itemView.tvDescription.text = description
                itemView.tvMaxTemperature.text = "$high"
                itemView.tvMinTemperature.text = "$low"
                itemView.setOnClickListener { itemClick(forecast) }
            }
        }
    }
}