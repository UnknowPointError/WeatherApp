package cn.example.sunnyWeather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/*************************
 * @ProjectName: SunnyWeather
 * @Dir_Path: app/src/main/java/cn/example/sunnyWeather
 * @Author: BarryAllen
 * @Time: 2021/11/12 0:53 星期五
 * TODO: Get the global Context
 **************************/

// TODO TOKEN is the token value applied by CaiYun Weather API
//  TOKEN是彩云天气API应用的令牌值

class SunnyWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = ""
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}