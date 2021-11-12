package cn.example.sunnyWeather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO MAIN: This object class mainly uses the
//  retrofit builder created for this purpose by using
//  the placeService interface and other interfaces
//  此单例类主要去使用PlaceService接口以及其他接口而为此创建的Retrofit构建器

// TODO baseUrl: Specify root path 指定根路径
// TODO addConverterFactory(GsonConverterFactory.create()):
//      Use GsonConverterLibrary  使用Gson转换库
// TODO create(): Call this function to create a dynamic proxy object
//  调用create()函数创建动态代理对象


object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}