package com.autowise.module.base.network

import com.autowise.module.base.AppContext
import com.autowise.module.base.R
import com.autowise.module.base.common.utils.ToastUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * User: chenyun
 * Date: 2022/05/05
 * Description:
 * FIXME
 */
abstract class BaseObserver<T> : Observer<BaseResponse<T>> {

    val fileTxt = AppContext.app.getString(R.string.common_request_failed)
    override fun onSubscribe(d: Disposable) {
    }

    final override fun onNext(t: BaseResponse<T>) {
       if (HandleError.handle(t)){
           return
       }
        if (t.status == "success") {
            onSuccess(t)
        } else {
            onFail(t)
        }
        onComplete()
    }

    final override fun onError(e: Throwable) {
        onError(fileTxt)
        onComplete()
    }


    open fun onSuccess(t: BaseResponse<T>) {
        ToastUtils.showToast(t.message)
    }

    /******* onFail 或者 onError 都需要单独处理时，onFail、onError都重写********/
    /**** 重写 super.onFail(t) ,会弹Toast****/
    open fun onFail(t: BaseResponse<T>) {
        onFailOrError(t.message)
    }

    /**** 重写 super.onError(t) ,会弹Toast****/
    open fun onError(e: String) {
        onFailOrError(fileTxt)
    }

    /******* onFail、onError统一处理时，重写onFailOrError就可以了********/
    open fun onFailOrError(msg: String?) {
        ToastUtils.showToast(msg ?: fileTxt)
    }


    override fun onComplete() {

    }
}