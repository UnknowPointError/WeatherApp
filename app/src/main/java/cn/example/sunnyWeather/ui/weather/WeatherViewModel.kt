package cn.example.sunnyWeather.ui.weather

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.example.sunnyWeather.logic.Repository
import cn.example.sunnyWeather.logic.model.Location as location
import cn.example.sunnyWeather.logic.network.PlaceService

/*************************
 * @ProjectName: SunnyWeather
 * @Dir_Path: app/src/main/java/cn/example/sunnyWeather/ui/weather
 * @Author: BarryAllen
 * @Time: 2021/11/13 23:08 星期六
 * TODO:
 **************************/
class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<location>()

    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng,location.lat)

    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = location(lng,lat)
    }
}