package com.autowise.module.base.common.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.autowise.module.base.AppContext;

import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by mars.yu on 2017/11/13.
 */

public class DeviceUtils {
  public static int getWidth(Context context) {
    return context.getResources()
            .getDisplayMetrics().widthPixels;
  }

  public static int getHeight(Context context) {
    return context.getResources()
            .getDisplayMetrics().heightPixels;
  }

  public static int getDpi(Context context) {
    return context.getResources()
            .getDisplayMetrics().densityDpi;
  }

  public static float getDensity(Context context) {
    return context.getResources()
            .getDisplayMetrics().density;
  }

  /**
   * 判断设备是否root
   *
   * @return the boolean{@code true}: 是<br>{@code false}: 否
   */
  public static boolean isDeviceRooted() {
    String su = "su";
    String[] locations =
            {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/xbin/",
                    "/data/local/bin/", "/data/local/"};
    for (String location : locations) {
      if (new File(location + su).exists()) {
        return true;
      }
    }
    return false;
  }

  /**
   * 获取设备系统版本号
   *
   * @return 设备系统版本号
   */
  public static int getSDKVersion() {
    return Build.VERSION.SDK_INT;
  }

  /**
   * 获取设备系统版本号名称
   *
   * @return 设备系统版本号名称
   */
  public static String getSDKVersionName() {
    return Build.VERSION.RELEASE;
  }

  /**
   * 获取设备AndroidID
   *
   * @return AndroidID
   */
  @SuppressLint("HardwareIds")
  public static String getAndroidID() {
    return Settings.Secure.getString(AppContext.getApp()
            .getContentResolver(), Settings.Secure.ANDROID_ID);
  }

  /**
   * 获取设备MAC地址
   * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
   * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
   *
   * @return MAC地址
   */
  public static String getMacAddress() {
    String macAddress = getMacAddressByWifiInfo();
    if (!"02:00:00:00:00:00".equals(macAddress)) {
      return macAddress;
    }
    macAddress = getMacAddressByNetworkInterface();
    if (!"02:00:00:00:00:00".equals(macAddress)) {
      return macAddress;
    }
    macAddress = getMacAddressByFile();
    if (!"02:00:00:00:00:00".equals(macAddress)) {
      return macAddress;
    }
    return "please open wifi";
  }

  /**
   * 获取设备MAC地址
   * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
   *
   * @return MAC地址
   */
  @SuppressLint("HardwareIds")
  private static String getMacAddressByWifiInfo() {
    try {
      @SuppressLint("WifiManagerLeak")
      WifiManager wifi = (WifiManager) AppContext.getApp()
              .getSystemService(Context.WIFI_SERVICE);
      if (wifi != null) {
        WifiInfo info = wifi.getConnectionInfo();
        if (info != null) {
          return info.getMacAddress();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "02:00:00:00:00:00";
  }

  /**
   * 获取设备MAC地址
   * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
   *
   * @return MAC地址
   */
  private static String getMacAddressByNetworkInterface() {
    try {
      List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
      for (NetworkInterface ni : nis) {
        if (!ni.getName()
                .equalsIgnoreCase("wlan0")) {
          continue;
        }
        byte[] macBytes = ni.getHardwareAddress();
        if (macBytes != null && macBytes.length > 0) {
          StringBuilder res1 = new StringBuilder();
          for (byte b : macBytes) {
            res1.append(String.format("%02x:", b));
          }
          return res1.deleteCharAt(res1.length() - 1)
                  .toString();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "02:00:00:00:00:00";
  }

  /**
   * 获取设备MAC地址
   *
   * @return MAC地址
   */
  private static String getMacAddressByFile() {
    ShellUtils.CommandResult result = ShellUtils.execCmd("getprop wifi.interface", false);
    if (result.result == 0) {
      String name = result.successMsg;
      if (name != null) {
        result = ShellUtils.execCmd("cat /sys/class/net/" + name + "/address", false);
        if (result.result == 0) {
          if (result.successMsg != null) {
            return result.successMsg;
          }
        }
      }
    }
    return "02:00:00:00:00:00";
  }

  /**
   * 获取设备厂商
   * <p>如Xiaomi</p>
   *
   * @return 设备厂商
   */

  public static String getManufacturer() {
    return Build.MANUFACTURER;
  }

  /**
   * 获取设备型号
   * <p>如MI2SC</p>
   *
   * @return 设备型号
   */
  public static String getModel() {
    String model = Build.MODEL;
    if (model != null) {
      model = model.trim()
              .replaceAll("\\s*", "");
    } else {
      model = "";
    }
    return model;
  }

  /**
   * 关机
   * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
   */
  public static void shutdown() {
    ShellUtils.execCmd("reboot -p", true);
    Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
    intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    AppContext.getApp()
            .startActivity(intent);
  }

  /**
   * 重启
   * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
   */
  public static void reboot() {
    ShellUtils.execCmd("reboot", true);
    Intent intent = new Intent(Intent.ACTION_REBOOT);
    intent.putExtra("nowait", 1);
    intent.putExtra("interval", 1);
    intent.putExtra("window", 0);
    AppContext.getApp()
            .sendBroadcast(intent);
  }

  /**
   * 重启
   * <p>需系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
   *
   * @param reason 传递给内核来请求特殊的引导模式，如"recovery"
   */
  public static void reboot(final String reason) {
    PowerManager mPowerManager = (PowerManager) AppContext.getApp()
            .getSystemService(Context.POWER_SERVICE);
    try {
      mPowerManager.reboot(reason);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 重启到recovery
   * <p>需要root权限</p>
   */
  public static void reboot2Recovery() {
    ShellUtils.execCmd("reboot recovery", true);
  }

  /**
   * 重启到bootloader
   * <p>需要root权限</p>
   */
  public static void reboot2Bootloader() {
    ShellUtils.execCmd("reboot bootloader", true);
  }


  /**
   * 获取设备id
   *  1. 获取ANDROID_ID
   *  2. 生成UUID
   *  //TODO: ANDROID_ID获取失败时，有没有更好的获取id方案
   *  OAID
   */
  public static String getDeviceId(Context context) {
    String deviceId = "";
    try {
      deviceId = getAndroidId(context);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (TextUtils.isEmpty(deviceId)) {
      UUID uuid = UUID.randomUUID();
      deviceId = uuid.toString().replace("-", "");
    }
    return deviceId;
  }

  /**
   * 获取IMEI码，需要权限
   */
  private static String getIMIEStatus(Context context) {
    TelephonyManager tm = (TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE);
    String deviceId = tm.getDeviceId();
    return deviceId;
  }

  /**
   * Mac地址
   */
  private static String getLocalMac(Context context) {
    WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    WifiInfo info = wifi.getConnectionInfo();
    return info.getMacAddress();
  }

  /**
   * Android Id
   */
  private static String getAndroidId(Context context) {
    String androidId = Settings.Secure.getString(
            context.getContentResolver(), Settings.Secure.ANDROID_ID);
    return androidId;
  }

  /**
   * 手机震动
   */
  public static void vibrate() {
    Vibrator vibrator = (Vibrator) AppContext.getApp().getSystemService(Context.VIBRATOR_SERVICE);
    vibrator.vibrate(150); //震动1秒
  }


  /**
   * 判断是否是全屏手机还是虚拟导航栏
   */
  public static boolean hasNavigationBar(Context context) {
    return getStatusBarHeight(context) < getNavigationBarHeight(context);
  }


  /**
   * 获取状态栏高度
   */
  public static int getStatusBarHeight(Context context) {
    Resources rs = context.getResources();
    int resourceId = rs.getIdentifier("status_bar_height", "dimen", "android");
    int statusHeight = rs.getDimensionPixelSize(resourceId);
    return statusHeight;
  }


  /**
   * 获取导航栏高度
   */
  public static int getNavigationBarHeight(Context context) {
    Resources rs = context.getResources();
    int navId = rs.getIdentifier("navigation_bar_height", "dimen", "android");
    int navHeight = rs.getDimensionPixelSize(navId);
    return navHeight;
  }


  public static String getDeviceName() {
    String result = "";
    try {
//      result = Settings.Secure.getString(AppContext.app.getContentResolver(), "bluetooth_name");
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
        Settings.Global.getString(AppContext.app.getContentResolver(), Settings.Global.DEVICE_NAME);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}
