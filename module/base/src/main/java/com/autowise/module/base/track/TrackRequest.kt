package com.autowise.module.base.track

import com.autowise.module.base.AppContext
import com.autowise.module.base.network.BaseRepository
import com.autowise.module.base.network.BaseResponse
import com.autowise.module.base.network.HeadParamsInterceptor
import com.autowise.module.base.network.HttpsUtils
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * User: chenyun
 * Date: 2022/9/22
 * Description:
 * FIXME
 */

object TrackRepository : BaseRepository<TrackApi>() {

    override fun createApi(): TrackApi {
        return TrackApi.create()
    }


    fun postTrackInfo(list: List<TrackEntity>): Observable<BaseResponse<TrackResponse>> {
        return commonApi.postTrackInfo(Gson().toJson(list))
    }

}

object TrackELKRepository : BaseRepository<TrackApi>() {

    override fun createApi(): TrackApi {
        return TrackApi.createElk()
    }

    fun postTrackInfoToElk(param: String, body: RequestBody): Observable<Unit> {
        return commonApi.postTrackInfoToElk(param, body)
    }

}

interface TrackApi {

    companion object {

        fun create(): TrackApi {
            return getRetrofit().create(TrackApi::class.java)
        }

        fun getRetrofit(): Retrofit {
            val logger =
                HttpLoggingInterceptor()/*.apply { level = HttpLoggingInterceptor.Level.BODY }*/

            val httpsUtils = HttpsUtils()
            val sslParams = httpsUtils.sslSocketFactory
            val cookieJar =
                PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(AppContext.app))

            val client =
                OkHttpClient.Builder().addInterceptor(logger)

                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .addInterceptor(HeadParamsInterceptor())
                    .cookieJar(cookieJar).build()

            return Retrofit.Builder().baseUrl(AppContext.envConfig.baseUrl).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        }

        fun createElk(): TrackApi {
            return getElkRetrofit().create(TrackApi::class.java)
        }

        fun getElkRetrofit(): Retrofit {
            val logger =
                HttpLoggingInterceptor()/*.apply { level = HttpLoggingInterceptor.Level.BODY }*/

            val httpsUtils = HttpsUtils()
            val sslParams = httpsUtils.sslSocketFactory
            val cookieJar =
                PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(AppContext.app))

            val client =
                OkHttpClient.Builder().addInterceptor(logger)
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .addInterceptor(ELKHeadParamsInterceptor())
                    .cookieJar(cookieJar).build()

            return Retrofit.Builder().baseUrl(AppContext.envConfig.elkUrl).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        }
    }

    /**
     *  上传用户行为日志
     */
    @POST("/v3/user-operate-log/create")
    @FormUrlEncoded
    fun postTrackInfo(@Field("json_data") json_data: String): Observable<BaseResponse<TrackResponse>>

//            val param="/test_index_xiebo_01/_bulk"
    /**
     *  上传用户行为日志
     */
    @POST("{param}")
    fun postTrackInfoToElk(
        @Path(value = "param", encoded = true) path: String,
        @Body body: RequestBody
    ): Observable<Unit>

}


