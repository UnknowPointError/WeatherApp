package cn.example.sunnyWeather.ui.weather

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.example.sunnyWeather.R
import cn.example.sunnyWeather.databinding.ActivityWeatherBinding
import cn.example.sunnyWeather.logic.model.Weather
import cn.example.sunnyWeather.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.*


class WeatherActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityWeatherBinding.inflate(layoutInflater) }
    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initViewModel()
    }

    private fun initViewModel() {
//        val decordView = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val i = WindowManager.LayoutParams()
            i.fitInsetsTypes = WindowInsets.Type.statusBars()
            window.insetsController?.hide(i.fitInsetsTypes)
            window.insetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_DEFAULT
        }
//        decordView.systemUiVisibility =
//            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        window.statusBarColor = Color.TRANSPARENT
        viewModel.apply()
        {
            if (locationLng.isEmpty()) {
                locationLng = intent.getStringExtra("location_lng") ?: ""
            }
            if (locationLat.isEmpty()) {
                locationLat = intent.getStringExtra("location_lat") ?: ""
            }
            if (placeName.isEmpty()) {
                placeName = intent.getStringExtra("place_name") ?: ""
            }
            viewModel.weatherLiveData.observe(this@WeatherActivity, Observer { result ->
                val weather = result.getOrNull()
                if (weather != null) {
                    showWeatherInfo(weather)
                } else {
                    Toast.makeText(this@WeatherActivity, "无法获取天气信息。", Toast.LENGTH_SHORT).show()
                    result.exceptionOrNull()?.printStackTrace()
                }
                mBinding.swipeRefresh.isRefreshing = false
            })
        }
        mBinding.swipeRefresh.setColorSchemeResources(R.color.design_default_color_primary)
        refreshWeather()
        mBinding.swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }

        mBinding.nowLayout.navBtn.setOnClickListener {
            mBinding.drawerLayout.openDrawer(GravityCompat.START)
        }

        mBinding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

        })
    }

    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        mBinding.swipeRefresh.isRefreshing = true
    }

    private fun showWeatherInfo(weather: Weather) {
        mBinding.apply {
            val realTime = weather.realtime
            val currentTempText = "${realTime.temperature.toInt()}°C"
            val currentPM25Text = "空气指数 ${realTime.airQuality.aqi.chn.toInt()}"
            val daily = weather.daily
            val days = daily.skycon.size
            nowLayout.apply {
                placeName.text = viewModel.placeName
                currentTemp.text = currentTempText
                currentSky.text = getSky(realTime.skycon).info
                currentAQI.text = currentPM25Text
                nowLayout.setBackgroundResource(getSky(realTime.skycon).bg)
            }
            forecastLayout.apply {
                forecastLayout.removeAllViews()
                for (i in 0 until days) {
                    val view = LayoutInflater.from(this@WeatherActivity)
                        .inflate(R.layout.forecast_item, forecastLayout, false)
                    val skycon = daily.skycon[i]
                    val temperature = daily.temperature[i]
                    val dataInfo = view.findViewById(R.id.dateInfo) as TextView
                    val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
                    val skyInfo = view.findViewById(R.id.skyInfo) as TextView
                    val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
                    val simpleDataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    if (skycon.dateTime != null) {
                        dataInfo.text = simpleDataFormat.format(skycon.dateTime)
                    }
                    val sky = getSky(skycon.value)
                    skyIcon.setImageResource(sky.icon)
                    skyInfo.text = sky.info
                    val tempText = "${temperature.min.toInt()}~${temperature.max.toInt()}℃"
                    temperatureInfo.text = tempText
                    forecastLayout.addView(view)
                }
            }
            lifeIndexLayout.apply {
                val lifeIndex = daily.lifeIndex
                coldRiskText.text = lifeIndex.coldRisk[0].desc
                dressingText.text = lifeIndex.dressing[0].desc
                ultravioletText.text = lifeIndex.ultraviolet[0].desc
                carwashingText.text = lifeIndex.carWashing[0].desc
                weatherLayout.visibility = View.VISIBLE
            }

        }
    }

/*    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        mBinding.swipeRefresh.isRefreshing = true
    }*/

}