//package com.autowise.module.base.webview
//
//import android.annotation.TargetApi
//import android.content.Context
//import android.os.Build
//import android.util.AttributeSet
//import android.webkit.WebSettings
//import android.webkit.WebView
//
///**
// * User: chenyun
// * Date: 2021/10/28
// * Description:
// * FIXME
// */
//open class DWebView : WebView {
//
//    private val APP_CACHE_DIR = context.filesDir.absolutePath + "/webCache"
//    private val jsInterfaces = mutableMapOf<String, Any>()
//    private val BRIDGE_NAME = "_dsbridge"
//
//    constructor(context: Context) : super(context)
//    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
//    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
//        context,
//        attrs,
//        defStyleAttr
//    )
//
//    private val webChromeClient = WebChromeClientImpl()
//
//
//    init {
//        initSetting()
//    }
//
//    /**
//     * WebSettings设置
//     */
//    private fun initSetting() {
//        setWebContentsDebuggingEnabled(true)
//        settings.apply {
//            val settings: WebSettings = this
//            val ua = settings.userAgentString
//            // 添加userAgent，方便前端网页判断请求来源
//            settings.setUserAgentString("$ua; app/1.0.0")
//            // 页面中要与JavaScript交互，需要设置为true
//            settings.javaScriptEnabled = true
//            // 设置允许JS弹窗
//            settings.javaScriptCanOpenWindowsAutomatically = true
//            //保存表单数据
//            settings.saveFormData = true
//            //将图片调整到适合webview的大小
//            settings.useWideViewPort = true
//            // 缩放至屏幕的大小
//            settings.loadWithOverviewMode = true
//            //不支持缩放
//            settings.setSupportZoom(false)
//            //设置内置的缩放控件，这个取决于setSupportZoom，如果setSupportZoom设置为false，则此处设置无效
//            //            settings.setBuildInZoomControls(true)
//            //设置可以访问文件，如果此处设置为false，则webview的input标签的type=‘file’将点击无响应
//            settings.allowFileAccess = true
//            //支持多窗口
//            settings.setSupportMultipleWindows(true)
//            //支持内容重新布局，实战发现此处如果设置为NARROW_COLUMN会导致某些手机显示不能撑满宽度问题
//            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
//            //支持自动加载图片
//            settings.loadsImagesAutomatically = true
//            //设置编码格式
//            settings.defaultTextEncodingName = "utf-8"
//            //设置默认字体大小
//            settings.defaultFontSize = 16
//            //特别注意，5.1以上默认禁止了https和http的混用，以下方式是开启
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
//            }
//            domStorageEnabled = true
//        }
//        super.setWebChromeClient(webChromeClient)
//
//        webViewClient = WebViewClientImpl()
//
////        addInternalJavascriptObject()
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
////            addJavascriptInterface(innerJavascriptInterface, BRIDGE_NAME)
//        }
////        settings.setUserAgentString(settings.userAgentString.toString() + BRIDGE_NAME)
//    }
//
//
//    override fun loadUrl(url: String, additionalHttpHeaders: MutableMap<String, String>) {
//        super.loadUrl(url, additionalHttpHeaders)
//    }
//
//
//    @TargetApi(11)
//    private fun removeUnsafeJs() {
//        if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 17) {
//            jsInterfaces.remove("searchBoxJavaBridge_")
//            jsInterfaces.remove("accessibilityTraversal")
//            jsInterfaces.remove("accessibility")
//        }
//    }
//
//    fun addJavascriptObject(name: String, jsObject: Any) {
//        jsInterfaces[name] = jsObject
//    }
//
//    fun getJavascriptObject(name: String): Any? {
//        return jsInterfaces[name]
//    }
//
//
//    private fun _evaluateJavascript(script: String) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            super.evaluateJavascript(script, null)
//        } else {
//            loadUrl("javascript:$script")
//        }
//    }
//
//    override fun destroy() {
//        super.destroy()
//    }
//}