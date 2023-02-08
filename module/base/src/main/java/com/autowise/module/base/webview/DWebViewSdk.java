package com.autowise.module.base.webview;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 *
 */
public class DWebViewSdk {
  private static final String  TAG      = "DWebViewSdk";
  private              Context mContext = null;
  private              boolean mIsDebug = false;

  public static DWebViewSdk getInstance() {
    return CoreSdkHolder.instance;
  }

  /***
   *
   * @param context 当前上下文
   * @param isDebug 是否是Debug
   * @param bugReportAppId bugReport SDK id
   */
  public void init(Context context, boolean isDebug, String bugReportAppId) {
    mContext = context.getApplicationContext();
    mIsDebug = isDebug;
  }

//  /**
//   * 开启x5引擎
//   */
//  public void startX5Env() {
//    new Thread(new Runnable() {
//      @Override
//      public void run() {
//        QbSdk.initX5Environment(mContext, new QbSdk.PreInitCallback() {
//          @Override
//          public void onCoreInitFinished() {
//            FDLog.d(TAG, " x5 browser onCoreInitFinished");
//          }
//
//          @Override
//          public void onViewInitFinished(boolean b) {
//            FDLog.d(TAG, " x5 browser onViewInitFinished");
//          }
//        });
//      }
//    }).start();
//  }

  /**
   * 获取上下文对象
   *
   * @return context
   */
  public Context getContext() {
    return mContext;
  }

  /**
   * 获取是否是Debug模式，还是release模式
   *
   * @return 返回是否是正式包
   */
  public boolean getIsDebug() {
    return mIsDebug;
  }

  private static class CoreSdkHolder {
    @SuppressLint("all")
    private static DWebViewSdk instance = new DWebViewSdk();
  }
}
