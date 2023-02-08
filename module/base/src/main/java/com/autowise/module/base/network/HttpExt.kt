package com.autowise.module.base.network

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * User: chenyun
 * Date: 2021/10/14
 * Description:
 * FIXME
 */
object HttpExt {
    /**
     * 是否需要登录
     */
//    fun <T> BaseResponse<T>.isNeedLogin(): Boolean {
//        return code.isNullOrBlank()
//    }

    /**
     * rxjava线程切换
     */
    fun <T> Observable<T>.ioToMain(): Observable<T> {
        return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}