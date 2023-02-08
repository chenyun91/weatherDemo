package com.autowise.module.base.webview;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Looper;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;


public class CommWebViewSdk {
    private static final String TAG = CommWebViewSdk.class.getSimpleName();
    private Context mContext = null;
    private CommWebSupport mCommWebSupport;
    private DownloadListener mDownloadListener;
    //    private              FDLifecycleHelper fdLifecycleHelper;
    private int mOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private boolean mIsOpenWhiteList = true;
    //    private              LoadingDialog     mLoadingDialog;
    private Handler mHander = new Handler(Looper.getMainLooper());

    public int getOrientation() {
        return mOrientation;
    }


    /**
     * DWebViewSdkHolder
     */
    private static class CommWebViewSdkHolder {
        private static CommWebViewSdk instance = new CommWebViewSdk();
    }

    public static CommWebViewSdk getInstance() {
        return CommWebViewSdkHolder.instance;
    }

    /***
     * 初始化
     *
     * @param context 当前上下文
     */
    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 是否开启白名单校验
     *
     * @param isOpen 默认为true
     */
    public void isOpenWhiteList(boolean isOpen) {
        mIsOpenWhiteList = isOpen;
    }

    /**
     * 是否开启白名单
     *
     * @return 是否开启
     */
    public boolean getIsOpenWhiteList() {
        return mIsOpenWhiteList;
    }

    /**
     * 添加Bridge
     *
     * @param packageName bridge的包名
     */
    public void setJSBridge(String packageName) {
        BridgeProvider.setPackageNames(packageName);
    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 通信类
     */
    public interface CommWebSupport {
        /**
         * @return 用户信息
         */
        Map<String, String> getUserInfo();

        /**
         * @return 环境
         */
        String getENV();

        /***
         * 自定义白名单
         * @return 自定义白名单
         */
        List<String> getWhiteListHost();
    }

    /**
     * 文件下载的外部实现
     */
    public interface DownloadListener {
        /**
         * WebView下载文件
         *
         * @param url                下载地址
         * @param userAgent          UA
         * @param contentDisposition
         * @param mimeType           mimetype
         * @param contentLength      文件大小
         */
        void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength);
    }

    public CommWebSupport getCommWebSupport() {
        return mCommWebSupport;
    }

    public void setCommWebSupport(CommWebSupport commWebSupport) {
        mCommWebSupport = commWebSupport;
    }

    public DownloadListener getDownloadListener() {
        return mDownloadListener;
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.mDownloadListener = downloadListener;
    }

    /**
     * 打开离线包
     *
     * @param path path ：module_10005/index?tab=1
     */
    public void open(String path) {
//        if (mContext == null) {
//            FDLog.e(TAG, "未初始化CommWebViewSdk");
//            return;
//        }
//        try {
//            CommWebViewActivity.start(mContext, encodeUrl(path));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 对mate的url进行处理（拼接fd://offline/）并encode
     *
     * @param path path ：module_10005/index?tab=1
     * @return fd://offline/module_10005/index?tab=1
     * @throws UnsupportedEncodingException exception
     */
//    public String encodeUrl(String path) throws UnsupportedEncodingException {
//        return URLEncoder.encode(CommWebConstants.DOMAIN + path, "utf-8");
//    }

    /**
     * 设置横竖屏
     *
     * @param orientation orientation
     */
//    public void restrictOrientation(@CommWebConstants.ScreenOrientation int orientation) {
//        this.mOrientation = orientation;
//    }

    /**
     * 设置CommWebViewActivity生命周期监听
     *
     * @param helper helper
     */
//    public void setLifecycleHelper(FDLifecycleHelper helper) {
//        fdLifecycleHelper = helper;
//    }
//
//    public FDLifecycleHelper getFDLifecycleHelper() {
//        return fdLifecycleHelper;
//    }


    /***
     * 回到main thread
     * @param runnable 线程runable
     */
    private void runOnUI(@NonNull Runnable runnable) {
        mHander.post(runnable);
    }

    /**
     * 显示loading
     *
     * @param activity activity
     */
    public void showLoading(Activity activity) {
        runOnUI(() -> {
//            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
//                return;
//            }
//            if (activity != null
//                    && !activity.isFinishing()
//                    && !activity.isDestroyed()
//                    ) {
//                if (mLoadingDialog == null
//                        || (mLoadingDialog != null && mLoadingDialog.getOwnerActivity() != activity)) {
//                    mLoadingDialog = new LoadingDialog(activity);
//                }
//                FDLog.d(TAG, "mLoadingDialog is show");
//                mLoadingDialog.show();
//            }
        });
    }

    /**
     * 关闭loading
     * @param activity activity
     */
//    public void hideLoading(Activity activity) {
//        runOnUI(() -> {
//            if (activity != null && mLoadingDialog != null && mLoadingDialog.isShowing()) {
//                FDLog.d(TAG, "mLoadingDialog is dismiss");
//                mLoadingDialog.dismiss();
//            }
//        });
//    }

    /**
     * 注销loading
     */
//    public void dismissLoading() {
//        if (mLoadingDialog != null) {
//            mLoadingDialog.dismiss();
//            mLoadingDialog = null;
//        }
//    }

    /**
     * 设置定位引擎
     * @param iWebLocation 引擎实现
     */
//    public void enableWebLocation(IWebLocation iWebLocation) {
//        this.mWebLocation = iWebLocation;
//    }

    /**
     * 定位
     * @param callback 结果回调
     */
//    public void locate(IWebLocationCallback callback) {
//        if (mWebLocation == null) {
//            FDLog.e(TAG, "未设置定位接口");
//        }
//        mWebLocation.requestLocate(callback);
//    }
}