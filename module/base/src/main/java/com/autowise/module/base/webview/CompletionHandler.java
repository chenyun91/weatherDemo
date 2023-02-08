package com.autowise.module.base.webview;

/**
 * Created by du on 16/12/31.
 * 异步方法时候用这个作为回调回传参数
 */

public interface  CompletionHandler<T> {
    void complete(T retValue);
    void complete();
    void setProgressData(T value);
}
