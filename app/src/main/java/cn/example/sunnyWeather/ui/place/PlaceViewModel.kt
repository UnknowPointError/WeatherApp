package cn.example.sunnyWeather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.example.sunnyWeather.logic.Repository
import cn.example.sunnyWeather.logic.model.Place

/*************************
 * @ProjectName: SunnyWeather
 * @Dir_Path: app/src/main/java/cn/example/sunnyWeather/ui/place
 * @Author: BarryAllen
 * @Time: 2021/11/13 18:14 星期六
 * TODO:
 **************************/
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query = query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}