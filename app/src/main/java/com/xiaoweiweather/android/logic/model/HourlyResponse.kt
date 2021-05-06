package com.xiaoweiweather.android.logic.model

import java.util.*

data class HourlyResponse(val status:String,val result: Result) {
    data class Result(val hourly:Hourly)
    data class Hourly(val temperature:List<Temperature>,val skycon:List<Skycon>)
    data class Skycon(val datetime: Date,val value:String)
    data class Temperature(val value:Float)
}

data class Hourlyitem(var temperature:Float, var skycon:HourlyResponse.Skycon)