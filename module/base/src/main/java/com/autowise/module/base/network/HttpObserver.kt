package com.autowise.module.base.network

import com.autowise.module.base.common.utils.ext.LogUtils.logE
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * User: chenyun
 * Date: 2021/10/14
 * Description:
 * FIXME
 */
//abstract class HttpObserver<T> : Observer<BaseResponse<T>> {
//
//    override fun onSubscribe(d: Disposable) {
//    }
//
//    override fun onNext(t: BaseResponse<T>) {
//        if (t.status == "success") {
//            onSuccess(t.data)
//        } else {
//            onFail(null)
//        }
//    }
//
//    override fun onError(e: Throwable) {
//        logE(e.message)
//        onFail(e)
//    }
//
//    override fun onComplete() {
//    }
//
//    abstract fun onSuccess(data: T?)
//
//    abstract fun onFail(e: Throwable?)
//
//}