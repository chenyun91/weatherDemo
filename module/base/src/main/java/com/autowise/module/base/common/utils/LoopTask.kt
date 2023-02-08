package com.autowise.module.base.common.utils

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.autowise.module.base.network.HttpExt.ioToMain
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * User: chenyun
 * Date: 2021/10/22
 * Description:
 *  1. 循环执行任务
 *  2. 自动感知生命周期
 *
 *      scheduleInMainThread //是否在主线程执行循环操作
 */
class LoopTask constructor(
    val lifecycleOwner: LifecycleOwner,
    val scheduleInMainThread: Boolean = false
) {
    private var disposable: Disposable? = null
    var isLoopStart = false

    fun loop(intervalTime: Long = 5L, action: (Long) -> Unit) {
        isLoopStart = true
        action.invoke(-1) //先执行一次
        this.lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_RESUME) {
                    disposable?.dispose()
                    val observable = if (scheduleInMainThread) {
                        Observable.interval(intervalTime, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                    } else {
                        Observable.interval(intervalTime, TimeUnit.SECONDS)
                    }
                    disposable = observable.subscribe {
                        action.invoke(it)
                    }
                } else if (event == Lifecycle.Event.ON_PAUSE) {
                    disposable?.dispose()
                }
            }
        })
    }

//    fun loop(action: (Long) -> Unit): Disposable {
//        isLoopStart = true
//        return Observable.interval(5, TimeUnit.SECONDS).subscribe {
//            action.invoke(it)
//        }
//    }

    fun stopLoop() {
        isLoopStart = false
        disposable?.dispose()
    }

}