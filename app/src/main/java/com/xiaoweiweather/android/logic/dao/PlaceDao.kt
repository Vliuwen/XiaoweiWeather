package com.xiaoweiweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.xiaoweiweather.android.XiaoweiWeatherApplication
import com.xiaoweiweather.android.logic.model.Place

object PlaceDao {

    fun savePlace(place: Place){
        sharePreferences().edit{
            putString("place",Gson().toJson(place))
        }
    }
    fun getSavedPlace():Place{
        val placeJson= sharePreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }
    fun isPlaceSaved()= sharePreferences().contains("place")

    private fun sharePreferences()=XiaoweiWeatherApplication.context.
            getSharedPreferences("xiaowei_weather",Context.MODE_PRIVATE)
}