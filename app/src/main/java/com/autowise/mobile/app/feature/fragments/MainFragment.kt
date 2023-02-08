package com.autowise.mobile.app.feature.fragments

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.autowise.mobile.app.R
import com.autowise.mobile.app.data.entity.Forecast
import com.autowise.mobile.app.databinding.FrgMainBinding
import com.autowise.mobile.app.feature.adapter.WeatherAdapter
import com.autowise.mobile.app.feature.viewmodel.MainVM
import com.autowise.module.base.BaseFragment

class MainFragment : BaseFragment<FrgMainBinding>() {

    val list = mutableListOf("110000", "310000", "440100", "440300", "210100")

    val vm: MainVM by viewModels()

    override fun getLayoutId() = R.layout.frg_main

    private val mAdapter: WeatherAdapter by lazy {
        WeatherAdapter(requireContext()) {
            findNavController().navigate(
                R.id.action_main_fragment_to_weather_detail_fragment,
                bundleOf(
                    "forecast" to it
                )
            )
        }
    }

    override fun initView() {
        v.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(),
                DividerItemDecoration.HORIZONTAL))
            adapter = mAdapter
        }

        vm.resultData.observe(this) {
            it?.apply {
                mAdapter.mList.addAll(it)
                mAdapter.notifyDataSetChanged()
            }
        }

        list.forEach {
            vm.getWeather(city = it)
        }
    }

}