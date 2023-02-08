package com.autowise.module.base.webview

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.webkit.*
import com.autowise.module.base.R
import com.autowise.module.base.common.configs.SCHEMA.WIACTION
import com.autowise.module.base.common.utils.ext.LogUtils.logI


/**
 * User: chenyun
 * Date: 2021/10/28
 * Description:
 * FIXME
 */
open class WebViewClientImpl : WebViewClient() {

    val DSBRIDGE = "dsbridge.js"
    val jsContent =
        "    var bridge={default:this,call:function(b,a,c){var e=\"\";\"function\"==typeof a&&(c=a,a={});a={data:void 0===a?null:a};if(\"function\"==typeof c){var g=\"dscb\"+window.dscb++;window[g]=c;a._dscbstub=g}a=JSON.stringify(a);if(window._dsbridge)e=_dsbridge.call(b,a);else if(window._dswk||-1!=navigator.userAgent.indexOf(\"_dsbridge\"))e=prompt(\"_dsbridge=\"+b,a);return JSON.parse(e||\"{}\").data},register:function(b,a,c){c=c?window._dsaf:window._dsf;window._dsInit||(window._dsInit=!0,setTimeout(function(){bridge.call(\"_dsb.dsinit\")},\n" +
                "0));\"object\"==typeof a?c._obs[b]=a:c[b]=a},registerAsyn:function(b,a){this.register(b,a,!0)},hasNativeMethod:function(b,a){return this.call(\"_dsb.hasNativeMethod\",{name:b,type:a||\"all\"})},disableJavascriptDialogBlock:function(b){this.call(\"_dsb.disableJavascriptDialogBlock\",{disable:!1!==b})}};\n" +
                "!function(){if(!window._dsf){var b={_dsf:{_obs:{}},_dsaf:{_obs:{}},dscb:0,dsBridge:bridge,close:function(){bridge.call(\"_dsb.closePage\")},_handleMessageFromNative:function(a){var e=JSON.parse(a.data),b={id:a.callbackId,complete:!0},c=this._dsf[a.method],d=this._dsaf[a.method],h=function(a,c){b.data=a.apply(c,e);bridge.call(\"_dsb.returnValue\",b)},k=function(a,c){e.push(function(a,c){b.data=a;b.complete=!1!==c;bridge.call(\"_dsb.returnValue\",b)});a.apply(c,e)};if(c)h(c,this._dsf);else if(d)k(d,this._dsaf);\n" +
                "else if(c=a.method.split(\".\"),!(2>c.length)){a=c.pop();var c=c.join(\".\"),d=this._dsf._obs,d=d[c]||{},f=d[a];f&&\"function\"==typeof f?h(f,d):(d=this._dsaf._obs,d=d[c]||{},(f=d[a])&&\"function\"==typeof f&&k(f,d))}}},a;for(a in b)window[a]=b[a];bridge.register(\"_hasJavascriptMethod\",function(a,b){b=a.split(\".\");if(2>b.length)return!(!_dsf[b]&&!_dsaf[b]);a=b.pop();b=b.join(\".\");return(b=_dsf._obs[b]||_dsaf._obs[b])&&!!b[a]})}}();\n" +
                "window.AWSDK =dsBridge"

    //拦截之前请求的URL。默认返回false
    //false，继续加载之前的url
    //返回true，终止之前加载的URL
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean {
        logI("shouldOverrideUrlLoading " + request?.url.toString())
        request?.url?.apply {
            if (scheme.equals(WIACTION, true)) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(toString().replace(scheme!!, WIACTION))
                view.context.startActivity(intent)
                return true
            }
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

//    override fun shouldInterceptRequest(
//        view: WebView?,
//        request: WebResourceRequest?
//    ): WebResourceResponse? {
//        logI("shouldInterceptRequest " + request?.url.toString())
//        return super.shouldInterceptRequest(view, request)
//    }

    //开始载入页面时调用此方法，在这里我们可以设定一个loading的页面，告诉用户程序正在等待网络响应。
    //如果是长链接请求，则会一直处在载入的状态。
    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
        logI("onPageStarted " + view.hashCode())
//        view.loadUrl("javascript:$jsContent")
        //TODO: webview载入js代码，存在缓存的时候，由于js还没注入完成，web端获取window.wiAction对象时为null。
        view.evaluateJavascript("javascript:$jsContent") {
            logI("----evaluateJavascript " + it)
        }
        super.onPageStarted(view, url, favicon)
    }

    //：在页面加载结束时调用。我们可以关闭loading 条，切换程序动作。
    override fun onPageFinished(view: WebView, url: String?) {
        super.onPageFinished(view, url)
        logI("onPageFinished " + view.url)
    }

    //开始加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
        logI("onLoadResource " + view?.url)

    }


    /*App里面使用webview控件的时候遇到了诸如404这类的错误的时候，若也显示浏览器里面的那种错误提示页面就显得很丑陋了，那么这个时候我们的app就需要加载一个本地的错误提示页面，即webview如何加载一个本地的页面
//步骤1：写一个html文件（error_handle.html），用于出错时展示给用户看的提示页面
//步骤2：将该html文件放置到代码根目录的assets文件夹下
//步骤3：复写WebViewClient的onRecievedError方法 //该方法传回了错误码，根据错误类型可以进行不同的错误分类处理
webView.setWebViewClient(new WebViewClient(){
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
          switch(errorCode) {
              case HttpStatus.SC_NOT_FOUND:
                   view.loadUrl("file:///android_assets/error_handle.html");
                   break;
        }}});*/
    //加载页面的服务器出现错误时（如404）调用。
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        logI("onReceivedError " + request?.url)
        if (request?.url.toString() == view?.url) {
            onReceiveError()
        }
    }


    //    处理https请求
    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
//        handler?.proceed();  //表示等待证书响应  // 接受所有网站的证书
         handler?.cancel(); //表示挂起连接，为默认方式
        // handler.handleMessage(null); //可做其他处理
//        logI("onReceivedSslError " + view.url)
//        AlertDialog.Builder(view.context).apply {
//            setMessage(context.getString(R.string.net_ssl_error))
//            setPositiveButton(context.getString(R.string.common_confirm)) { dialog, which ->
////                handler?.proceed()// 接受https所有网站的证书
//                handler?.cancel()
//            }
//            setNegativeButton(context.getString(R.string.common_cancel)) { dialog, which ->
//                handler?.cancel()
//            }
//            create()
//            show()
//        }
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        logI("onReceivedHttpError" + " code${errorResponse?.statusCode} " + request?.url)

        if (request?.url.toString() == view?.url) {
            onReceiveError()
        }
    }

    open fun onReceiveError() {
        logI("加载失败")
    }

}