package cn.example.sunnyWeather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// TODO MAIN: Unified network data source access portal 统一的网络数据源访问入口

object SunnyWeatherNetWork {

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    // 1. Create a dynamic processing object for the placeService interface
    // 1. 为placeService接口创建动态处理对象
    private val placeService = ServiceCreator.create<PlaceService>()

    // 2. Call the functions of the interface to execute the request to initiate the search for city data
    // 2. 调用接口的函数执行发起搜索城市数据请求
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    // 3.When calling the searchPlace function, execute the await function
    // and then suspend the current coroutine with the suspendCoroutine function
    // The coroutine does not resume execution until the resume or resumeWithException function is called
    // 通过调用searchPlace函数时，去执行await函数，然后执行suspendCoroutine函数挂起当前协程
    // 直到调用resume或者resumeWithException函数才让协程恢复执行
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    val runTimeException = RuntimeException("response body is null")
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(runTimeException)
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun getDailyWeather(lng: String, lat: String) =
        weatherService.getDailyWeather(lng = lng, lat = lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng = lng, lat = lat).await()

}