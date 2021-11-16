package cn.example.sunnyWeather.logic.dao

import android.content.Context
import androidx.core.content.edit
import cn.example.sunnyWeather.SunnyWeatherApplication
import cn.example.sunnyWeather.logic.model.Place
import com.google.gson.Gson

/*************************
 * @ProjectName: SunnyWeather
 * @Dir_Path: app/src/main/java/cn/example/sunnyWeather/logic/dao
 * @Author: BarryAllen
 * @Time: 2021/11/15 18:45 星期一
 * TODO:
 **************************/
object PlaceDao {

    fun savePlace(place: Place) {
        sharedPreferences().edit{
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() =
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}