package com.autowise.module.base.network

import com.autowise.module.base.AppContext
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * User: chenyun
 * Date: 2021/10/15
 * Description:
 * FIXME
 */
object NetWorkManager {

    fun getRetrofit(): Retrofit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val httpsUtils = HttpsUtils()
        val sslParams = httpsUtils.sslSocketFactory
        val cookieJar =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(AppContext.app))

        val client =
            OkHttpClient.Builder().addInterceptor(logger)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
//                .addInterceptor(HeadParamsInterceptor())
//                .cookieJar(cookieJar)
                .build()

        return Retrofit.Builder().baseUrl(AppContext.envConfig.baseUrl).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }
}