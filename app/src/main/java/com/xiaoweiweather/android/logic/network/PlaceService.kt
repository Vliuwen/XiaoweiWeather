package com.xiaoweiweather.android.logic.network

import com.xiaoweiweather.android.XiaoweiWeatherApplication
import com.xiaoweiweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${XiaoweiWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String): Call<PlaceResponse>
}