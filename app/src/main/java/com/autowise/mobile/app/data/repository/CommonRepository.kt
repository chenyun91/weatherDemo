package com.autowise.mobile.app.data.repository

import com.autowise.mobile.app.common.configs.Constants
import com.autowise.mobile.app.data.api.CommonApi
import com.autowise.mobile.app.data.entity.Weather
import com.autowise.module.base.network.HttpExt.ioToMain
import io.reactivex.Observable


object CommonRepository {

    private val commonApi = CommonApi.create()


    fun getWeather(
        key: String = Constants.AMAP_KEY,
        city: String,
        extensions: String = "all"
    ): Observable<Weather> {
        return commonApi.getWeather(key, city, extensions).ioToMain()
    }
}