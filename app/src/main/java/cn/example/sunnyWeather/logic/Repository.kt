package cn.example.sunnyWeather.logic

import android.util.Log
import androidx.lifecycle.liveData
import cn.example.sunnyWeather.logic.dao.PlaceDao
import cn.example.sunnyWeather.logic.model.Place
import cn.example.sunnyWeather.logic.model.Weather
import cn.example.sunnyWeather.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetWork.getRealtimeWeather(lng = lng, lat = lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetWork.getDailyWeather(lng = lng, lat = lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                Log.e("realtimeResponse", ":${realtimeResponse}")
                Log.e("dailyResponse", ":${dailyResponse}")
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure<Weather>(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is  ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context = context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun savePlace(place: Place) = PlaceDao.savePlace(place = place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}