package com.autowise.module.base.network

import com.autowise.module.base.common.utils.ext.LogUtils
import com.autowise.module.base.track.*
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

/**
 * User: chenyun
 * Date: 2022/5/31
 * Description:
 * FIXME
 */
class MyWebSocketListener(
    val onFailure: () -> Unit = {},
    val onMessage: (String) -> Unit = {},
    val onOpen: (WebSocket) -> Unit = {},
) : WebSocketListener() {
    val logStr = StringBuilder()
    var lastMessage = ""

    /**
     * wss是否存活
     */
    var isKeepAlive = false

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        output("Closed : $reason")
        isKeepAlive = false
        super.onClosed(webSocket, code, reason)
        uploadWsData("Closed : code: $code reason= $reason")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        output("Closing : $reason")
        super.onClosing(webSocket, code, reason)
    }

    //10111
    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        output("Failure : " + t.message)
        isKeepAlive = false
        super.onFailure(webSocket, t, response)
        onFailure()
        uploadWsData("Failure : " + t.message)
    }

    //10112
    override fun onMessage(webSocket: WebSocket, text: String) {
        output("Received : $text")
        super.onMessage(webSocket, text)
        onMessage(text)
        lastMessage = text
    }

    //10110
    override fun onOpen(webSocket: WebSocket, response: Response) {
        output("Open : $response")
        isKeepAlive = true
        super.onOpen(webSocket, response)
        onOpen(webSocket)
        logStr.append("Open :" + response.request.url)
    }

    private fun output(s: String) {
        LogUtils.logD("websockt:", s);
        LogUtils.saveWsLogInfo("websockt:" + s)
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