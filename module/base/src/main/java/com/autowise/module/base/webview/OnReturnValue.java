package com.autowise.module.base.webview;

/**
 * Created by du on 16/12/31.
 * 调用js方法，js回调回写回来的参数
 */

public interface OnReturnValue<T> {
    void onValue(T retValue);
}
