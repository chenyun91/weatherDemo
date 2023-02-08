package com.autowise.module.base.common.utils

import android.app.Activity
import android.content.Context
import com.autowise.module.base.AppContext
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException


/**
 * User: chenyun
 * Date: 2022/5/18
 * Description:
 * FIXME
 */
object AppUtils {

    /**
     * 获取 versionCode
     */
    fun getVersionCode(): Int {
        var versionCode = 0
        try {
            versionCode = AppContext.app.packageManager.getPackageInfo(AppContext.app.packageName, 0).versionCode
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionCode
    }

    /**
     * 获取versionName
     */
    fun getVersionName(): String {
        return getVersionName(AppContext.app)
    }

    fun getVersionName(context: Context): String {
        var versionName = ""
        try {
            versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionName
    }


    /**
     * 通过反射的方式获取当前正在显示的Activity实例
     * https://blog.csdn.net/wusejiege6/article/details/100848791
     */
    fun getActivity(): Activity? {
        var activityThreadClass: Class<*>? = null
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread")
            val activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null)
            val activitiesField: Field = activityThreadClass.getDeclaredField("mActivities")
            activitiesField.isAccessible = true
            val activities = activitiesField.get(activityThread) as Map<*, *>
            for (activityRecord in activities.values) {
                val activityRecordClass: Class<*> = activityRecord!!.javaClass
                val pausedField: Field = activityRecordClass.getDeclaredField("paused")
                pausedField.isAccessible = true
                if (!pausedField.getBoolean(activityRecord)) {
                    val activityField: Field = activityRecordClass.getDeclaredField("activity")
                    activityField.isAccessible = true
                    return activityField.get(activityRecord) as Activity?
                }
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return null
    }

}