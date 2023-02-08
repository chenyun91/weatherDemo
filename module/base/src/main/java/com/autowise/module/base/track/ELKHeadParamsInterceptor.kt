package com.autowise.module.base.track

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * User: chenyun
 * Date: 2022/12/6
 * Description:
 * FIXME
 */
class ELKHeadParamsInterceptor : Interceptor {
    /**
     * 给POST请求添加默认参数
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        // 添加Header参数
        val newRequest = request.newBuilder()
            .addHeader(
                "Authorization",
                "ApiKey U2M1VzVvUUJKNUkzd2traXJlLTE6bE8xRHlPYjdRR0toazBIaU5aamh5dw=="
            )
            .build()
        return chain.proceed(newRequest)
    }
}