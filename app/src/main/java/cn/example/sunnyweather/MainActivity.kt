package cn.example.sunnyweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.example.sunnyweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}