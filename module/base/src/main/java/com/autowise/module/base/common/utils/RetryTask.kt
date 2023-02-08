package com.autowise.module.base.common.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * User: chenyun
 * Date: 2022/1/4
 * Description: 5s后重试
 * FIXME
 */
class RetryTask {
    private var disposable: Disposable? = null
    private var isStop = false

    /**
     * 失败重试
     */
    fun failRetry( mainThread: Boolean = false,action: () -> Unit) {
        if (isStop) {
            return
        }
        disposable?.dispose()
        val observable = if (mainThread) {
            Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
        } else {
            Observable.interval(5, TimeUnit.SECONDS)
        }
        disposable = observable.subscribe {
            action.invoke()
        }
    }

    fun stop() {
        isStop = true
        disposable?.dispose()
    }

    fun start() {
        isStop = false
    }
}