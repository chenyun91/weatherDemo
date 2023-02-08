package com.autowise.mobile.app.feature.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.autowise.mobile.app.data.entity.Cast
import com.autowise.mobile.app.databinding.ItemWeatherBinding

class WeatherDetailAdapter constructor(
    val mContext: Context
) : RecyclerView.Adapter<WeatherDetailAdapter.ViewHolder>() {

    val mList = mutableListOf<Cast>()

    fun setData(list: List<Cast>) {
        mList.clear()
        mList.addAll(list)
    }

    inner class ViewHolder constructor(val v: ItemWeatherBinding) :
        RecyclerView.ViewHolder(v.root) {

        fun bind(entity: Cast) {
//            val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
//            layoutParams.bottomMargin = if (layoutPosition == 0) margin else 0
            v.tvDesc.text = entity.toString()
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
    }

    override fun getItemCount() = mList.size
}