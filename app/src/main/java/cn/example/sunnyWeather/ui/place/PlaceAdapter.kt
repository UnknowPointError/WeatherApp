package cn.example.sunnyWeather.ui.place

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import cn.example.sunnyWeather.R
import cn.example.sunnyWeather.databinding.ActivityMainBinding
import cn.example.sunnyWeather.databinding.ActivityWeatherBinding
import cn.example.sunnyWeather.databinding.PlaceItemBinding
import cn.example.sunnyWeather.logic.model.Place
import cn.example.sunnyWeather.ui.weather.WeatherActivity

/*************************
 * @ProjectName: SunnyWeather
 * @Dir_Path: app/src/main/java/cn/example/sunnyWeather/ui/place
 * @Author: BarryAllen
 * @Time: 2021/11/13 18:31 星期六
 * TODO:
 **************************/
class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(mBinding: PlaceItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        val placeName: TextView = mBinding.placeName
        val placeAddress: TextView = mBinding.placeAddress
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mBinding = PlaceItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        )
        val holder = ViewHolder(mBinding)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]
            val activity = fragment.activity
            if (activity is WeatherActivity) {
                val drawerLayout: DrawerLayout = parent.rootView.findViewById(R.id.drawerLayout)
                drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            } else {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                fragment.startActivity(intent)
                activity?.finish()
            }
            fragment.viewModel.savePlace(place = place)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount(): Int = placeList.size
}