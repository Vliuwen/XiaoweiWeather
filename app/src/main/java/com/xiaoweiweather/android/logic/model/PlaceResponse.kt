package com.xiaoweiweather.android.logic.model

import com.google.gson.annotations.SerializedName

//由搜索城市数据接口返回的JSON格式定义数据模型

//地区信息(请求状态，地区数组)
data class PlaceResponse(val status: String, val places: List<Place>)

//地区(地区名字，地区经纬度,地区地址)
//使用SerializedName注解建立JSON字段与Kotlin字段映射，避免命名规范不一致
data class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)

//经纬度(纬度，经度)
data class Location(val lng: String, val lat: String)