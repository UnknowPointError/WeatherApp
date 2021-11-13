package cn.example.sunnyWeather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.example.sunnyWeather.databinding.ActivityMainBinding

// TODO MAIN: App's main Activity APP的主活动

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}