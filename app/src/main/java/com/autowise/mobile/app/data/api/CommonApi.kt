package com.autowise.mobile.app.data.api

import com.autowise.mobile.app.data.entity.Weather
import com.autowise.module.base.network.NetWorkManager
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * User: chenyun
 * Date: 2021/10/13
 * Description:
 * FIXME
 */
interface CommonApi {

    companion object {
        fun create(): CommonApi {
            return NetWorkManager.getRetrofit().create(CommonApi::class.java)
        }
    }

    @GET("/v3/weather/weatherInfo")
    fun getWeather(
        @Query("key") key: String,
        @Query("city") city: String,
        @Query("extensions") extensions: String
    ): Observable<Weather>
}