package com.autowise.module.base.network

import com.autowise.module.base.common.utils.ext.LogUtils
import okhttp3.logging.HttpLoggingInterceptor

/**
 * User: chenyun
 * Date: 2022/8/9
 * Description:
 * FIXME
 */
class HttpLogger:HttpLoggingInterceptor.Logger{
    override fun log(message: String) {
        LogUtils.saveHttpLogInfo(message)
    }
}