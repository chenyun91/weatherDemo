package com.autowise.mobile.app.feature.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.autowise.mobile.app.data.entity.Forecast
import com.autowise.mobile.app.databinding.ItemWeatherBinding

class WeatherAdapter constructor(
    val mContext: Context,
    val onItemClick: (Forecast) -> Unit
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    val mList = mutableListOf<Forecast>()

    fun setData(list: List<Forecast>) {
        mList.clear()
        mList.addAll(list)
    }

    inner class ViewHolder constructor(val v: ItemWeatherBinding) :
        RecyclerView.ViewHolder(v.root) {

        fun bind(entity: Forecast) {
//            val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
//            layoutParams.bottomMargin = if (layoutPosition == 0) margin else 0
            v.tvCity.text = entity.city
            if (entity.casts.isNotEmpty()) {
                v.tvDesc.text = entity.casts[0].toString()
            }
            v.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
        holder.v.root.setOnClickListener {
            onItemClick(mList[position])
        }
    }

    override fun getItemCount() = mList.size
}