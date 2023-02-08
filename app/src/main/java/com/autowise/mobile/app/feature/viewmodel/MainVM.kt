package com.autowise.mobile.app.feature.viewmodel

import androidx.lifecycle.MutableLiveData
import com.autowise.mobile.app.data.entity.Forecast
import com.autowise.mobile.app.data.entity.Weather
import com.autowise.mobile.app.data.repository.CommonRepository
import com.autowise.module.base.BaseVM
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


class MainVM : BaseVM() {

    val citys = mutableMapOf(
        "北京" to "110000",
        "上海" to "310000",
        "广州" to "440100",
        "深圳" to "440300",
        "沈阳" to "210100"
    )


    val resultData = MutableLiveData<List<Forecast>>()


    /**
     * 获取天气数据
     */
    fun getWeather(city: String) {
        CommonRepository.getWeather(city = city)
            .subscribe(object : Observer<Weather> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Weather) {
                    if (t.status == 1 && t.infocode == 10000) {
                        resultData.value = t.forecasts
                    }
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

            })
    }


}