package com.autowise.module.base.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.autowise.module.base.AppContext;
import com.autowise.module.base.R;
import com.autowise.module.base.common.configs.AppCoreInit;
import com.autowise.module.base.common.configs.URI;
import com.autowise.module.base.common.utils.AppUtils;
import com.autowise.module.base.common.utils.StatusBarUtils;
import com.autowise.module.base.common.utils.ToastUtils;
import com.autowise.module.base.common.utils.UiUtils;
import com.autowise.module.base.common.utils.arouter.RouterUtils;
import com.autowise.module.base.common.utils.ext.LogUtils;
import com.autowise.module.base.data.UserInfoBean;
import com.autowise.module.base.network.HandleError;
import com.autowise.module.constant.RoutePath;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

import static com.autowise.module.base.common.configs.SCHEMA.WIACTION;

/**
 * 提供给web端调用的bridge
 */
public class JsCommonApi {

    private static final String TAG = "JsCommonApi";
    protected WeakReference<Context> mContext;
    private Handler mHander = new Handler(Looper.getMainLooper());

    private Map<String, CompletionHandler> mHandlerMap;

    public JsCommonApi(Context context) {
        mContext = new WeakReference<>(context);
        mHandlerMap = new HashMap<>();
    }

    /***
     * 回到main thread
     * @param runnable 线程runable
     */
    protected void runOnUI(@NonNull Runnable runnable) {
        mHander.post(runnable);
    }

    /**
     * 1.获取用户信息
     */
    @JavascriptInterface
    public void getUserInfo(Object data, CompletionHandler<Object> completionHandler) {
        LogUtils.INSTANCE.logD(TAG, "getUserInfo  入参数--->  " + data.toString());
        JSONObject jsonObject = new JSONObject();
        if (mContext.get() == null) {
            completionHandler.complete(null);
            return;
        }
        try {
            jsonObject.put("token", AppContext.INSTANCE.getToken());
            jsonObject.put("version", AppUtils.INSTANCE.getVersionName(mContext.get()));
            jsonObject.put("platform", "android");
            jsonObject.put("language", AppContext.INSTANCE.getLocale().toLanguageTag());
            jsonObject.put("env", AppContext.INSTANCE.getEnvConfig().getEnv());
            jsonObject.put("topMargin", UiUtils.INSTANCE.px2dp(mContext.get(), StatusBarUtils.getStatusBarHeight(mContext.get())));
            UserInfoBean userInfo = AppContext.INSTANCE.getUserInfo();
            if (userInfo != null) {
                jsonObject.put("userName", userInfo.getUsername());
                jsonObject.put("userId", userInfo.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        completionHandler.complete(jsonObject);
    }

    /**
     * 2.打开新页面
     */
    @JavascriptInterface
    public void openNativePage(Object data, CompletionHandler<Object> completionHandler) {
        LogUtils.INSTANCE.logD(TAG, "openNativePage  入参数--->  " + data.toString());
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            JSONObject jsonObject = new JSONObject(data.toString());
            String url = jsonObject.optString("url");

            Uri uri = Uri.parse(url);
            if (uri.getScheme().equalsIgnoreCase(WIACTION)) {
                url= url.replace(uri.getScheme(), WIACTION);
            }
            String vehicleId = uri.getQueryParameter("vehicle_id");
            String closeCurrent = uri.getQueryParameter("close_current"); //是否关闭当前页面
            if (url.startsWith(URI.VehicleMap)) {
                RouterUtils.INSTANCE.navigateToVehicleSelectionActivity(vehicleId, RoutePath.VEHICLE_MAP_ACTIVITY);
            } else if (url.startsWith(URI.SweepReport)) {
                RouterUtils.INSTANCE.navigateToVehicleSelectionActivity(vehicleId, RoutePath.VEHICLE_SWEEP_REPORT_ACTIVITY);
            } else if (url.startsWith(URI.StartVehicle)) {
                RouterUtils.INSTANCE.navigateToVehicleSelectionActivity(vehicleId, RoutePath.VEHICLE_START_FRAGMENT);
            } else if (url.startsWith(URI.ChooseTask)) {
                RouterUtils.INSTANCE.navigateToVehicleSelectionActivity(vehicleId, RoutePath.VEHICLE_ROUT_TASK_ACTIVITY);
            } else {
                intent.setData(Uri.parse(url));
                mContext.get().startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        completionHandler.complete(jsonObject);
    }

    /**
     * 3.关闭当前页面
     */
    @JavascriptInterface
    public void closePage(Object data, CompletionHandler<Object> completionHandler) {
        LogUtils.INSTANCE.logD(TAG, "closePage  入参数--->  " + data.toString());
        runOnUI(() -> {
            if (mContext.get() instanceof Activity) {
                ((Activity) mContext.get()).onBackPressed();
            }
        });
        JSONObject jsonObject = new JSONObject();
        completionHandler.complete(jsonObject);
    }


    /**
     * 4. Toast提示弹窗
     */
    @JavascriptInterface
    public void showToast(Object data, CompletionHandler<Object> completionHandler) {
        runOnUI(() -> {
            ToastUtils.INSTANCE.showToast(data.toString(), Toast.LENGTH_LONG);
        });
        JSONObject jsonObject = new JSONObject();
        completionHandler.complete(jsonObject);
    }

    /**
     * 6.设置页面标题
     */
    @JavascriptInterface
    public void setTitle(Object data, CompletionHandler<Object> completionHandler) {
        JSONObject jsonObject = new JSONObject();
        completionHandler.complete(jsonObject);
    }


    /**
     * 7.强制升级
     */
    @JavascriptInterface
    public void forceUpgrade(Object data, CompletionHandler<Object> completionHandler) {
        HandleError.INSTANCE.handleForceUpgrade();
        JSONObject jsonObject = new JSONObject();
        completionHandler.complete(jsonObject);
    }

    /**
     * 8.退出登录
     */
    @JavascriptInterface
    public void logout(Object data, CompletionHandler<Object> completionHandler) {
        runOnUI(() -> {
            ToastUtils.INSTANCE.showToast(AppContext.app.getString(R.string.common_login_expired),Toast.LENGTH_SHORT);
            AppCoreInit.INSTANCE.logout();
        });
        JSONObject jsonObject = new JSONObject();
        completionHandler.complete(jsonObject);
    }

    /**
     * 插入handler
     *
     * @param methodKey key
     * @param handler   handler
     */
    private void putHandler(String methodKey, CompletionHandler handler) {
        if (mHandlerMap != null) {
            mHandlerMap.put(methodKey, handler);
        }
    }

    /**
     * 绑定handler
     *
     * @param methodKey key
     * @return handler
     */
    private CompletionHandler obtainHandler(String methodKey) {
        return mHandlerMap != null ? mHandlerMap.get(methodKey) : null;
    }

    /**
     * 页面销毁的时候调用，清除资源
     */
    public void onDestroy() {
        if (mContext != null) {
            mContext.clear();
        }
    }

}
