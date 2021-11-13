package cn.example.sunnyWeather.ui.place

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import cn.example.sunnyWeather.R
import cn.example.sunnyWeather.databinding.PlaceItemBinding
import cn.example.sunnyWeather.logic.model.Place

/*************************
 * @ProjectName: SunnyWeather
 * @Dir_Path: app/src/main/java/cn/example/sunnyWeather/ui/place
 * @Author: BarryAllen
 * @Time: 2021/11/13 18:31 星期六
 * TODO:
 **************************/
class PlaceAdapter(private val fragment: Fragment, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(mBinding: PlaceItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        val placeName: TextView = mBinding.placeName
        val placeAddress: TextView = mBinding.placeAddress
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mBinding = PlaceItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        )
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount(): Int = placeList.size
}