package com.autowise.module.base.common.utils.time

import android.os.CountDownTimer
import android.util.Log

/**
 * User: chenyun
 * Date: 2022/5/9
 * Description:
 * FIXME
 */
class TimerWrapper {

    var time = 1

    var timer: CountDownTimer? = null

    var requestTime = -1

    var isCleared = false

    /**
     * @param totalSecond  从totalSecond秒开始倒计时
     * @param intervalSecond  倒计时时间间隔 s
     * @param stickySecond  最短时间间隔， 持续触发事件
     * @param onTimeOutStickyEvent 超过时间持续触发事件 ,  true ：消费事件。重置事件
     */
    fun start(
        totalSecond: Int,
        intervalSecond: Int = 1,
        stickySecond: Int = 0,
        onTickCallback: ((Int) -> Unit) = { },
        onTimeOutStickyEvent: (() -> Boolean) = { true},
        onFinishCallback: (() -> Unit) = { }
    ) {
        requestTime = totalSecond
        timer?.cancel()
        isCleared = false
        onTimeOutStickyEvent()//先触发一次
        timer = object : CountDownTimer(totalSecond * 1000L + 300, intervalSecond * 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                if (isCleared) return
                time = (millisUntilFinished / (intervalSecond * 1000L)).toInt()
                Log.i("TimerWrapper", "time= " + time+" \trequestTime= "+requestTime)
                onTickCallback(time)
                if (requestTime - time >= stickySecond) {
                    if (onTimeOutStickyEvent()) {
                        requestTime = time
                    }
                }
            }

            override fun onFinish() {
                onFinishCallback()
            }
        }
        timer?.start()
    }

    fun clear() {
        isCleared = true
        timer?.cancel()
        timer = null
        time = 0
    }
}