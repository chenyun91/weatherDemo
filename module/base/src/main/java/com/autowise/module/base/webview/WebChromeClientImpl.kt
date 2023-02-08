package com.autowise.module.base.webview

import android.net.Uri
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.autowise.module.base.common.utils.ext.LogUtils
import com.autowise.module.base.common.utils.ext.LogUtils.logI

/**
 * User: chenyun
 * Date: 2021/10/28
 * Description:
 * FIXME
 */
// 辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等。
open class WebChromeClientImpl : WebChromeClient() {

    //获得网页的加载进度并显示
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        //progress.setText(progress);
        LogUtils.logI("onProgressChanged: "+newProgress)
    }

    //获取Web页中的标题
    override fun onReceivedTitle(view: WebView?, title: String?) {
        // titleview.setText(title)；
        LogUtils.logI("onReceivedTitle: "+title)
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        super.onShowCustomView(view, callback)
        LogUtils.logI("onShowCustomView: ")
    }

    override fun onHideCustomView() {
        super.onHideCustomView()
        LogUtils.logI("onHideCustomView: ")
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        LogUtils.logI("onConsoleMessage: "+consoleMessage)
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        LogUtils.logI("onShowFileChooser: ")
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
    }
}
