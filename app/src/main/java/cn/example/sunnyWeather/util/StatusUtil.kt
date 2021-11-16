package cn.example.sunnyWeather.util

import android.app.Activity
import android.os.Build
import android.view.WindowInsetsController
import androidx.core.view.WindowInsetsCompat
import cn.example.sunnyWeather.R

object StatusUtil {

    fun Activity.hindSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {

        }
    }
}