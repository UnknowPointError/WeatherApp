package cn.example.sunnyWeather.ui.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import cn.example.sunnyWeather.MainActivity
import cn.example.sunnyWeather.R
import cn.example.sunnyWeather.databinding.FragmentPlaceBinding
import cn.example.sunnyWeather.ui.weather.WeatherActivity

/*************************
 * @ProjectName: SunnyWeather
 * @Dir_Path: app/src/main/java/cn/example/sunnyWeather/ui/place
 * @Author: BarryAllen
 * @Time: 2021/11/13 18:42 星期六
 * TODO:
 **************************/
class PlaceFragment : Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }
    private lateinit var mBinding: FragmentPlaceBinding
    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPlaceBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        val layoutManager = LinearLayoutManager(activity)
        mBinding.apply {
            recyclerView.layoutManager = layoutManager
            adapter = PlaceAdapter(this@PlaceFragment, viewModel.placeList)
            recyclerView.adapter = adapter
            searchPlaceEdit.addTextChangedListener { editable ->
                val content = editable.toString()
                if (content.isNotEmpty()) {
                    viewModel.searchPlaces(content)
                } else {
                    recyclerView.visibility = View.GONE
                    viewModel.placeList.clear()
                    adapter.notifyDataSetChanged()
                }
            }
            viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
                val places = result.getOrNull()
                if (places != null) {
                    recyclerView.visibility = View.VISIBLE
                    bgImageView.visibility = View.GONE
                    viewModel.placeList.clear()
                    viewModel.placeList.addAll(places)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(activity, "未能查询到任何地点。", Toast.LENGTH_SHORT).show()
                    result.exceptionOrNull()
                }
            })
        }
    }

}

