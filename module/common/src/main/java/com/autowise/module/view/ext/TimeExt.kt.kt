package com.autowise.module.view.ext

import java.lang.Exception
import java.text.SimpleDateFormat

/**
 * User: chenyun
 * Date: 2021/11/16
 * Description:
 * FIXME
 */

/**
 * 时间戳转为 mm-dd HH:mm:ss
 *
 */
fun Long.toDateAndTime(): String {
    return SimpleDateFormat("MM-dd HH:mm:ss").format(this * 1000)
}

fun String?.toDateAndTime(): String {
    if (this == null) return ""
    var result = ""
    try {
        result = SimpleDateFormat("MM-dd HH:mm:ss").format(this.toLong() * 1000)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return result
}