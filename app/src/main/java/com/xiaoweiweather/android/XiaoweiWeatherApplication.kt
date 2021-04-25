package com.xiaoweiweather.android

import android.app.Application
import android.content.Context

class XiaoweiWeatherApplication :Application(){
    companion object{
        const val TOKEN="zD6qUsgh5PurlAxK"
        @SuppressWarnings("StaticFileLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}