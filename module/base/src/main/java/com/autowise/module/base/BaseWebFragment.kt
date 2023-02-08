package com.autowise.module.base

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.*
import androidx.annotation.CallSuper
import com.autowise.module.base.databinding.BaseFrgWebviewBinding
import com.autowise.module.base.webview.JsCommonApi
import com.autowise.module.base.webview.WebChromeClientImpl
import com.autowise.module.base.webview.WebViewClientImpl
import com.autowise.module.view.ext.ViewExt.toVisible

/**
 * User: chenyun
 * Date: 2021/10/27
 * Description:
 * FIXME
 */
abstract class BaseWebFragment : BaseFragment<BaseFrgWebviewBinding>() {


    override fun getLayoutId() = R.layout.base_frg_webview
    var showError = true

    /**
     * 在该方法中，进行初始化配置
     */
    @CallSuper
    override fun initView() {
        setContainerView(v.clContainer)
        WebView.setWebContentsDebuggingEnabled(AppContext.isDebug)
        v.webView.webViewClient = if (showError) LocalWebViewClient() else WebViewClientImpl()
        v.webView.webChromeClient = LocalWebChromeClientImpl()
        v.webView.addJavascriptObject(JsCommonApi(requireContext()), null) //jsbridge方法
        v.webView.setBackgroundColor(0)
        val settings: WebSettings = v.webView.settings
        settings.mediaPlaybackRequiresUserGesture = false
        settings.allowFileAccess = true
        loadUrl()
    }

    /**
     * 在该方法中，load url
     */
    abstract fun loadUrl()

    /**
     * 设置加载失败时的页面
     */
    inner class LocalWebViewClient : WebViewClientImpl() {
        var isError = false

        /**
         *  界面开始显示出来
         */
        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            isError = false
        }

        override fun onPageFinished(view: WebView, url: String?) {
            super.onPageFinished(view, url)
            if (!isError) {
                view.postDelayed({ showNormal() }, 250L) //TODO: 不延迟会短暂出现机器人图标的错误页面，需要延迟一段时间。
            }
        }

//        override fun onReceivedHttpError(
//            view: WebView?,
//            request: WebResourceRequest?,
//            errorResponse: WebResourceResponse?
//        ) {
//            super.onReceivedHttpError(view, request, errorResponse)
//            showError()
//            isError = true
//        }
//
//        override fun onReceivedError(
//            view: WebView?,
//            request: WebResourceRequest?,
//            error: WebResourceError?
//        ) {
//            super.onReceivedError(view, request, error)
//            showError()
//            isError = true
//        }

        override fun onReceiveError() {
            super.onReceiveError()
            showError()
            isError = true
        }
    }

    /**
     * 控制进度条以及loading显示
     */
    inner class LocalWebChromeClientImpl : WebChromeClientImpl() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressChanged(newProgress)
        }
    }

    val progressTotalWidth: Int by lazy {
        v.clContainer.width
    }

    /**
     * 控制进度条以及loading显示
     */
    open fun progressChanged(newProgress: Int) {
        v.viewProgress.toVisible(newProgress in 1..99)
        val layoutParams = v.viewProgress.layoutParams
        layoutParams.width = progressTotalWidth * newProgress / 100
        v.viewProgress.requestLayout()
    }


    override fun refreshData() {
        v.webView.reload()
    }

    override fun onResume() {
        super.onResume()
        v.webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        v.webView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        v.webView.apply {
            (parent as ViewGroup).removeView(this)
            destroy()
        }
    }

}