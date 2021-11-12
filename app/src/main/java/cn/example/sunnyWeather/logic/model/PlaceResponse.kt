package cn.example.sunnyWeather.logic.model

import com.google.gson.annotations.SerializedName

/*************************
 * @ProjectName: SunnyWeather
 * @Dir_Path: app/src/main/java/cn/example/sunnyWeather/logic/model
 * @Author: BarryAllen
 * @Time: 2021/11/12 1:03 星期五
 * TODO: The attribute definitions of these data classes are defined
 *  according to the JSON format returned by the CaiYun Weather API
 *  这些数据类的属性定义是根据彩云天气API返回的JSON格式定义的
 **************************/

// TODO The naming of the returned JSON data may be inconsistent
//  with the naming of Kotlin, so the annotation @SerializedName
//  返回的JSON数据的命名可能不一致 由于命名为Kotlin，因此注释@SerializedName

data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)
