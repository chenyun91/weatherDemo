package com.autowise.mobile.app.common.utils

/**
 * User: chenyun
 * Date: 2021/12/21
 * Description:
 *  防止点击过快
 */
class DoubleEventFilter<T> constructor(val event: (T) -> Unit) {

    val MIN_CLICK_DELAY_TIME = 500 //两个点击最小间隔
    var lastClickTime = 0L

    fun onEvent(t: T) {
        val curTime = System.currentTimeMillis()
        if (curTime - lastClickTime > MIN_CLICK_DELAY_TIME) { //只有大于才会走逻辑
            lastClickTime = curTime
            event.invoke(t)
        } else {
            lastClickTime = curTime
        }
    }
}

