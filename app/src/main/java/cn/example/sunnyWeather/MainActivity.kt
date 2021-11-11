package cn.example.sunnyWeather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.example.sunnyweather.databinding.ActivityMainBinding

// TODO App's main program

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}