package com.xiaoweiweather.android.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xiaoweiweather.android.R
import com.xiaoweiweather.android.logic.model.HourlyResponse
import com.xiaoweiweather.android.logic.model.Hourlyitem
import com.xiaoweiweather.android.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter( private val hourlyitemList:List<Hourlyitem>):
    RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val timeInfo:TextView= view.findViewById(R.id.hourInfo)
        val skyIcon:ImageView = view.findViewById(R.id.hourlySkyIcon)
        val temperatureInfo:TextView=view.findViewById(R.id.hourlyTemperatureInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.hourly_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourlyitem=hourlyitemList[position]

        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        holder.timeInfo.text=simpleDateFormat.format(hourlyitem.skycon.datetime)

        val sky= getSky(hourlyitem.skycon.value)
        holder.skyIcon.setImageResource(sky.icon)
        val tempText="${hourlyitem.temperature.toInt()}℃"
        holder.temperatureInfo.text=tempText
    }

    override fun getItemCount()=hourlyitemList.size

}