package com.autowise.module.view.ext
/**
 * 速度 m/s 转换为 km/h
 * 结果保留2位小数
 */
fun Float.speed2KmPerH(): String {
    return String.format("%.2f", this * 3.6)
    //return DecimalFormat("#.00").format(this*3.6)
}
