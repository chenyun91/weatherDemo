package com.autowise.mobile.app.data.entity

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

/**
 * User: chenyun
 * Date: 2023/2/8
 * Description:
 * FIXME
 */
data class Weather(
    val count: String,
    val forecasts: List<Forecast>,
    val info: String,
    val infocode: Int,
    val status: Int
)

@Parcelize
data class Forecast(
    val adcode: String,
    val casts: List<Cast>,
    val city: String,
    val province: String,
    val reporttime: String
) : Parcelable {
    fun getDesc(): String {
        return "城市名：${city},省份名：${province},区域编码：${adcode},数据发布的时间：${reporttime}"
    }
}

@Parcelize
data class Cast(
    val date: String,
    val daypower: String,
    val daytemp: String,
    val daytemp_float: String,
    val dayweather: String,
    val daywind: String,
    val nightpower: String,
    val nighttemp: String,
    val nighttemp_float: String,
    val nightweather: String,
    val nightwind: String,
    val week: String
) : Parcelable {
    override fun toString(): String {
        return "日期：${date},周：${week}\n" +
                "白天天气：${dayweather},白天气温：${daytemp},白天风向：${daywind},白天风力：${daypower}," +
                "\n夜间天气：${nightweather},夜间气温：${nighttemp},晚上风向：${daywind},晚上风力：${daypower}"
    }
}