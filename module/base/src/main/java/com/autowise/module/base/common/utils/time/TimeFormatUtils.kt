package com.autowise.module.base.common.utils.time

import com.autowise.module.base.AppContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * User: chenyun
 * Date: 2022/6/7
 * Description:
 * FIXME
 */
object TimeFormatUtils {
    val FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
    val FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm"
    val FORMAT_HH_MM_SS_SSS = "HH:mm:ss:SSS"

    /**
     * @param time 时间戳（毫秒）
     * @param format "yyyy-MM-dd HH:mm:ss"
     */
    public fun getFormatString(time: Long, format: String): String {
        try {
            val date = Date(time)
            val normalFormat = SimpleDateFormat(format)
            return normalFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}