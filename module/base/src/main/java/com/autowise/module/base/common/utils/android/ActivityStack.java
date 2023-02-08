package com.autowise.module.base.common.utils.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import java.util.Stack;

public class ActivityStack {
  private              Stack<Activity> mActivities = new Stack<Activity>();
  private static final String          TAG         = "ActivityStack";
  private static       ActivityStack   INSTANCE;

  private ActivityStack() {
  }

  public static ActivityStack getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ActivityStack();
    }
    return INSTANCE;
  }

  public void popActivity() {
      Activity activity = mActivities.pop();
  }

  public void popActivity(Activity activity) {
    mActivities.remove(activity);
  }

  public Activity topActivity() {
    return mActivities.empty() ? null : mActivities.lastElement();
  }

  public void pushActivity(Activity activity) {
    mActivities.add(activity);
  }

  public void popAllActivity(boolean isForceClose) {
    while (mActivities.size() > 0) {
      popActivity();
    }
    if (isForceClose) {
      Process.killProcess(Process.myPid());
      System.exit(-1);
    }
  }

  /**
   * 移除"栈内除栈顶所有页面"，只保留当前页面，即栈顶唯一页面
   */
  public void popAllActivityExceptTop() {
    while (mActivities.size() > 1) {
      Activity activity = mActivities.get(0);
      mActivities.remove(activity);
      if (activity != null) {
        activity.finish();
        activity = null;
      }
    }
  }

  /**
   * 结束进程并重启app(会有黑屏现象)
   */
  public void reStartApp(Context mContext, Class<?> className) {
    Intent intent = new Intent(mContext, className);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    mContext.startActivity(intent);

    Process.killProcess(Process.myPid());
  }

  public void popActivityUntilCls(Class<?> clz) {
    while (mActivities.size() > 1) {
      if (topActivity().getClass() != clz) {
        popActivity();
      } else {
        break;
      }
    }
  }

  /**
   * 移除"栈内除栈所有页面"，只保留栈底界面
   */
  public void popAllActivityExceptBottom() {
    int size = mActivities.size();
    for (int i = size - 1; i >= 1; i--) {
      Activity activity = mActivities.get(i);
      mActivities.remove(activity);
      if (activity != null) {
        activity.finish();
      }
    }
  }

  public int size() {
    return mActivities.size();
  }

  public Activity activityAt(int position) {
    return position < mActivities.size() ? mActivities.elementAt(position) : null;
  }
}
