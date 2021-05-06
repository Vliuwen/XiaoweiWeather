package com.xiaoweiweather.android.logic.network

import com.xiaoweiweather.android.XiaoweiWeatherApplication
import com.xiaoweiweather.android.logic.model.DailyResponse
import com.xiaoweiweather.android.logic.model.HourlyResponse
import com.xiaoweiweather.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    //lng：纬度  lat:经度
    @GET("v2.5/${XiaoweiWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng")lng:String,@Path("lat")lat:String):
            Call<RealtimeResponse>

    @GET("v2.5/${XiaoweiWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng:String,@Path("lat")lat:String):
            Call<DailyResponse>

    @GET("v2.5/${XiaoweiWeatherApplication.TOKEN}/{lng},{lat}/hourly.json?hourlysteps=24")
    fun getHourlyWeather(@Path("lng")lng:String,@Path("lat")lat:String):
            Call<HourlyResponse>
}