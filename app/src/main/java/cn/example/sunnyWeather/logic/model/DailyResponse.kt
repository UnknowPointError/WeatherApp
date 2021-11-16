package cn.example.sunnyWeather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyResponse(val status: String, val result: Result) {
    data class Result(val daily: Daily)
    data class Daily(
        val temperature: List<Temperature>,
        val skycon: List<Skycon>,
        @SerializedName("life_index") val lifeIndex: Lifeindex
    )

    data class Temperature(val max: Float, val min: Float)
    data class Skycon(val value: String, @SerializedName("date") val dateTime: Date)
    data class Lifeindex(
        val coldRisk: List<LifeDescription>,
        val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>,
        val dressing: List<LifeDescription>
    )

    data class LifeDescription(val desc: String)
}