package com.autowise.module.base.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor

/**
 * User: chenyun
 * Date: 2021/10/26
 * Description:
 * FIXME
 */
class WebSocketHelper constructor(private val url: String) {

    private var webSocket: WebSocket? = null
    private val client: OkHttpClient = OkHttpClient.Builder()
//        .addInterceptor(HttpLoggingInterceptor(HttpLogger()).apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        })
        .build()//TODO:设置单个请求响应的超时时间30s ，30s内未收到服务端ws的回复，则失败
    private val request: Request = Request.Builder().url(url).build()


    fun connect(listener: MyWebSocketListener) {
        if (!listener.isKeepAlive) {
            webSocket = client.newWebSocket(request, listener)
        }
    }

    fun send(str: String) {
        webSocket?.send(str)
    }

//    fun disConnect() {
//        webSocket?.close(100, "")
//    }

    fun cancel() {
        webSocket?.cancel()
    }


}

