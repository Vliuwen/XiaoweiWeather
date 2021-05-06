package com.xiaoweiweather.android.logic


import androidx.lifecycle.liveData
import com.xiaoweiweather.android.logic.dao.PlaceDao
import com.xiaoweiweather.android.logic.model.Place
import com.xiaoweiweather.android.logic.model.Weather
import com.xiaoweiweather.android.logic.network.XiaoweiWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlaces(query:String)= liveData(Dispatchers.IO) {
        val result=try {
            val placeResponse=XiaoweiWeatherNetwork.searchPlaces(query)
            if (placeResponse.status=="ok"){    //服务器响应状态
                val places=placeResponse.places
                Result.success(places)  //打包成功结果
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))  //打包失败结果
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)    //发送
    }

    fun refreshWeather(lng:String,lat:String)= fire(Dispatchers.IO) {
            //协程作用域
            coroutineScope {
                //分别获取并响应成功后才会进一步执行
                val deferredRealtime = async {
                    XiaoweiWeatherNetwork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily=async {
                    XiaoweiWeatherNetwork.getDailyWeather(lng, lat)
                }
                val deferredHourly=async {
                    XiaoweiWeatherNetwork.getHourlyWeather(lng, lat)
                }
                val realtimeResponse=deferredRealtime.await()
                val dailyResponse=deferredDaily.await()
                val hourlyResponse=deferredHourly.await()
                //响应状态都是ok，则将Realtime和Daily对象取出并封装到一个Weather对象中
                if(realtimeResponse.status=="ok"&&dailyResponse.status=="ok"&&hourlyResponse.status=="ok"){
                    val weather= Weather(realtimeResponse.result.realtime,hourlyResponse.result.hourly,dailyResponse.result.daily)
                    Result.success(weather) //包装
                }else{
                    //异常处理
                    Result.failure(
                            RuntimeException(
                                "realtime response is ${realtimeResponse.status}"+
                                "daily response is ${dailyResponse.status}"+
                                "hourly response is ${hourlyResponse.status}"
                            )
                    )
                }
            }
        }

    //提供一个统一的入口函数进行try-catch处理 并发送执行结果
    private fun<T> fire(context:CoroutineContext,block:suspend ()->Result<T>)= liveData<Result<T>>(context) {
        val result=try {
            block()
        }catch (e:Exception){
            Result.failure<T>(e)
        }
        emit(result)
    }

    fun savePlace(place: Place)=PlaceDao.savePlace(place)
    fun getSavedPlace()=PlaceDao.getSavedPlace()
    fun isPlaceSaved()=PlaceDao.isPlaceSaved()

}