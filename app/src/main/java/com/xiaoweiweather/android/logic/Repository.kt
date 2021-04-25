package com.xiaoweiweather.android.logic


import androidx.lifecycle.liveData
import com.xiaoweiweather.android.logic.model.Place
import com.xiaoweiweather.android.logic.network.XiaoweiWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

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
}