package com.autowise.module.base.network

import com.autowise.module.base.common.utils.ext.LogUtils
import com.autowise.module.base.track.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor

/**
 * User: chenyun
 * Date: 2022/10/25
 * Description:
 * FIXME
 */
class WsHelper constructor(url: String, mListener: AbstractWsListener) {

    private var webSocket: WebSocket? = null
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val request: Request = Request.Builder().url(url).build()


    var mRequestParams: String? = null

    fun connect(requestParams: String? = null) {
        mRequestParams = requestParams
        webSocket?.cancel()
        webSocket = client.newWebSocket(request, mWebSocketListener)
    }

    /**
     * 重连
     */
    fun reconnect() {
        webSocket?.cancel()
        webSocket = client.newWebSocket(request, mWebSocketListener)
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


    private val mWebSocketListener = object : WebSocketListener() {
        val logStr = StringBuilder()
        var lastMessage = ""

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            mRequestParams?.apply { //发送请求参数
                webSocket.send(this)
                output("send : $this")
            }
            output("Open : $response")
            mListener.onOpen(webSocket)
            logStr.append("Open :" + response.request.url)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            output("Received : $text")
//            output("Received : --------")
            lastMessage = text
            try {
                mListener.onMessage(text)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            webSocket.cancel()
            output("Closed : $reason")
            uploadWsData("Closed : code: $code reason= $reason")
            mListener.onClosed()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            webSocket.cancel()
            output("Failure : " + t.message)
            uploadWsData("Failure : " + t.message)
            mListener.onFailure()
        }

        private fun output(s: String) {
            LogUtils.logD("websocket:", Thread.currentThread().name + "\t" + s);
//            LogUtils.saveWsLogInfo("websockt:" + s)
        }


        private fun uploadWsData(s: String) {
            logStr.append("\nReceived :$lastMessage\n")
            logStr.append(s)
            TrackDataBaseUtils.addNewData(
                TrackEntity(
                    EventType.CUSTOM,
                    EventID.WSS_CONNECT,
                    PageID.WSS,
                    logStr.toString()
                )
            )
            logStr.clear()
        }
    }

}

abstract class AbstractWsListener {
   open fun onFailure(){}
    open fun onClosed(){}
    open fun onMessage(text: String){}
    open fun onOpen(webSocket: WebSocket){}
}