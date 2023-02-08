package com.autowise.module.base.common.utils.doucleclick

import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * User: chenyun
 * Date: 2022/12/5
 * Description: 设置长按触摸事件
 * FIXME
 */
class PressedChangeListener constructor(
    val viewEnable: () -> Boolean,
    val action: (Boolean) -> Unit
) : View.OnTouchListener {
    val TAG = "PressedChangeListener"

    var isComplete = false

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        Log.i(TAG, "onTouch: " + event.action+"\t"+v.isPressed)
        if (!viewEnable()) {
            return true
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isComplete = false
                action(true)
            }
            MotionEvent.ACTION_MOVE -> {
                if (!v.isPressed && !isComplete) {
                    isComplete = true
                    action(false)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (!isComplete) {
                    isComplete = true
                    action(false)
                }
            }
        }
        return false
    }
}

fun View.setOnPressedChangeListener(viewEnable: () -> Boolean, action: (Boolean) -> Unit) {
    /**
     * @param viewEnable 提前检查 按钮是否可用 。false，不可用，不触发pressed事件
     */
    setOnTouchListener(PressedChangeListener(viewEnable, action))
}