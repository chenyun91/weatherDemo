package com.autowise.module.base.webview;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.autowise.module.base.common.utils.ext.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class BridgeProvider {

    private static ArrayList<String> packageNames;
    private static String TAG = "BridgeProvider  -->";

    static {
        packageNames = new ArrayList<>();
    }

    private ArrayList jsBridges;

    /**
     * 不允许自己创建
     */
    public BridgeProvider() {
        jsBridges = new ArrayList();
    }

    public static void setPackageNames(String packageName) {
        if (!TextUtils.isEmpty(packageName) && !packageNames.contains(packageName)) {
            packageNames.add(packageName);
        }
    }


    /**
     * onDestory
     */
    public void onDestroy() {
        LogUtils.INSTANCE.logD(TAG, "onDestroy is run");
        for (Object object : jsBridges) {
            if (object != null) {
                Class<?> cls = object.getClass();
                try {
                    Method method = cls.getDeclaredMethod("onDestroy");
                    if (null == method) {
                        continue;
                    }
                    method.setAccessible(true);
                    method.invoke(object);
                    LogUtils.INSTANCE.logD(TAG, "this bridge name is " + object.getClass()
                            .getSimpleName() + " onDestroy is run");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 显示dialog弹框
     */
    public void showLoading() {
        for (Object object : jsBridges) {
            if (object != null) {
                Class<?> cls = object.getClass();
                try {
                    Method method = cls.getDeclaredMethod("showLoading", Object.class);
                    if (null == method) {
                        continue;
                    }
                    method.setAccessible(true);
                    method.invoke(object, "");
                    LogUtils.INSTANCE.logD(TAG, "this bridge name is " + object.getClass()
                            .getSimpleName() + "showLoading is run");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 隐藏dialog弹框
     */
    public void hideLoading() {
        for (Object object : jsBridges) {
            if (object != null) {
                Class<?> cls = object.getClass();
                try {
                    Method method = cls.getDeclaredMethod("hideLoading", Object.class);
                    if (null == method) {
                        continue;
                    }
                    method.setAccessible(true);
                    method.invoke(object, "");
                    LogUtils.INSTANCE.logD(TAG, "this bridge name is " + object.getClass()
                            .getSimpleName() + "hideLoading is run");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Object object : jsBridges) {
            if (object != null) {
                Class<?> cls = object.getClass();
                try {
                    Method method = cls.getDeclaredMethod("onActivityResult", int.class, int.class, Intent.class);
                    if (null == method) {
                        continue;
                    }
                    method.setAccessible(true);
                    method.invoke(object, requestCode, resultCode, data);
                    LogUtils.INSTANCE.logD(TAG, "this bridge name is " + object.getClass()
                            .getSimpleName() + "onActivityResult is run");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        for (Object object : jsBridges) {
            if (object != null) {
                Class<?> cls = object.getClass();
                try {
                    Method method = cls.getDeclaredMethod("onRequestPermissionsResult", int.class, String[].class, int[].class);
                    if (null == method) {
                        continue;
                    }
                    method.setAccessible(true);
                    method.invoke(object, requestCode, permissions, grantResults);
                    LogUtils.INSTANCE.logD(TAG, "this bridge name is " + object.getClass()
                                                                                  .getSimpleName() + "onRequestPermissionsResult is run");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
