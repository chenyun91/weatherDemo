package com.autowise.module.base.common.utils.ext

import androidx.lifecycle.MutableLiveData

/**
 * User: chenyun
 * Date: 2022/12/28
 * Description:
 * FIXME
 */

object LiveDataExt {
    /**
     * 当发生变化再进行赋值 同步
     * */
    fun <T> MutableLiveData<T>.setValueOnChange(it: T?) {
        if (value != it) {
            value = it
        }
    }

    /**
     * 当发生变化再进行赋值 异步
     * */
    fun <T> MutableLiveData<T>.postValueOnChange(it: T?) {
        if (value != it) {
            postValue(it)
        }
    }
}