package com.autowise.module.base.network

import com.autowise.module.base.AppContext
import com.autowise.module.base.common.utils.AppUtils
import com.autowise.module.base.common.utils.ext.LogUtils.logI
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 *
 */
class HeadParamsInterceptor : Interceptor {
    /**
     * 给POST请求添加默认参数
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        //添加Query参数
        val httpUrl = request.url.newBuilder()
            .addQueryParameter("device_id", AppContext.deviceId)
            .build()
        // 添加Header参数
        val newRequest = request.newBuilder()
            .addHeader("Accept-Language", AppContext.locale.toLanguageTag())
            .addHeader("Authorization", AppContext.token ?: "")
            .addHeader("Version", AppUtils.getVersionName(AppContext.app)+"."+AppUtils.getVersionCode())
            .addHeader("Platform", "Android")
            .url(httpUrl)
            .build()
        logI("token= "+AppContext.token+" Accept-Language= "+AppContext.locale.toLanguageTag())
        return chain.proceed(newRequest)
    }
}