package com.xiaoweiweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.xiaoweiweather.android.logic.Repository
import com.xiaoweiweather.android.logic.model.Place

class PlaceViewModel:ViewModel() {
    private val searchLiveData=MutableLiveData<String>()
    val placeList=ArrayList<Place>()    //对界面上显示的城市数据进行缓存，防止屏幕旋转时数据丢失
    val placeLiveData=Transformations.switchMap(searchLiveData){query->
        Repository.searchPlaces(query)
    }
    fun searchPlaces(query:String){
        searchLiveData.value=query
    }

    

}