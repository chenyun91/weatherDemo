1. 前进、后退网页

    //是否可以后退
    Webview.canGoBack()
    
    //后退网页
    Webview.goBack()
    
    //是否可以前进
    Webview.canGoForward()
    
    //前进网页
    Webview.goForward()
    
    //以当前的index为起始点前进或者后退到历史记录中指定的steps
    //如果steps为负数则为后退，正数则为前进
    Webview.goBackOrForward(intsteps)
   

    //back键控制网页回退
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
         mWebView.goBack();
         return true;
    }
    return super.onKeyDown(keyCode, event);
    }

2. 清除缓存
    //清除网页访问留下的缓存
    //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
    Webview.clearCache(true);
   
    //清除当前webview访问的历史记录
    //只会webview访问历史记录里的所有记录除了当前访问记录
    Webview.clearHistory()；
   
    //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
    Webview.clearFormData()；
   

3. WebSettings类
   对WebView进行配置和管理；
   

4. WebViewClient
    对于长链接的vis请求，
    i. 成功会回调
        onLoadResource
        onPageStarted  
   ii. 失败会回调
        a.突然断网
            onPageStarted 
            onReceivedError 
            onPageFinished
        b.网页请求失败
            onLoadResource
           onReceivedHttpError
           onPageStarted
           onPageFinished