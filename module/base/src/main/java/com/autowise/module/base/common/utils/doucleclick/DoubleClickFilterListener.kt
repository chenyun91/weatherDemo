package com.autowise.module.base.common.utils.doucleclick

import android.view.View

/**
 * User: chenyun
 * Date: 2021/12/20
 * Description:
 *  防止点击过快
 */
abstract class DoubleClickFilterListener : View.OnClickListener {
    val MIN_CLICK_DELAY_TIME = 500 //两个点击最小间隔
    var lastClickTime = 0L

    override fun onClick(v: View?) {
        val curTime = System.currentTimeMillis()
        if (curTime - lastClickTime > MIN_CLICK_DELAY_TIME) { //只有大于才会走逻辑
            lastClickTime = curTime
            noDoubleClick(v)
        } else {
            lastClickTime = curTime
        }
    }

    abstract fun noDoubleClick(v: View?)
}


fun View.setNoDoubleClickListener(action: (View?) -> Unit) {
    setOnClickListener(object : DoubleClickFilterListener() {
        override fun noDoubleClick(v: View?) {
            action.invoke(v)
        }
    })
}