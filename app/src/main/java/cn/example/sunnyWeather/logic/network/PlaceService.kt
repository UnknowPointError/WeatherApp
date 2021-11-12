package cn.example.sunnyWeather.logic.network

import cn.example.sunnyWeather.SunnyWeatherApplication
import cn.example.sunnyWeather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// TODO MAIN: The current interface is mainly used to access the
//  retrofit interface of the City Search API
//  当前接口主要用于访问城市搜索API的Retrofit接口

// TODO When you call the searchPlaces function
//  Retrofit will initiate a GET request to access the address configured in @GET
//  调用searchPlaces函数时 Retrofit将启动GET请求以访问@GET中配置的地址

interface PlaceService {

    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}