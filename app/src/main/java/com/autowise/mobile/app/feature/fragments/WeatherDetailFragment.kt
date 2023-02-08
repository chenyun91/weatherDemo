package com.autowise.mobile.app.feature.fragments

import androidx.recyclerview.widget.LinearLayoutManager
import com.autowise.mobile.app.R
import com.autowise.mobile.app.data.entity.Forecast
import com.autowise.mobile.app.databinding.FrgWeatherDetailBinding
import com.autowise.mobile.app.feature.adapter.WeatherDetailAdapter
import com.autowise.module.base.BaseFragment

class WeatherDetailFragment : BaseFragment<FrgWeatherDetailBinding>() {

    override fun getLayoutId() = R.layout.frg_weather_detail

    private val mAdapter: WeatherDetailAdapter by lazy {
        WeatherDetailAdapter(requireContext())
    }

    override fun initView() {
        v.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }

        arguments?.getParcelable<Forecast>("forecast")?.apply {
            v.tvDesc.text = getDesc()
            v.titleLayout.tvTitle.text = city
            mAdapter.setData(casts)
        }
    }


}