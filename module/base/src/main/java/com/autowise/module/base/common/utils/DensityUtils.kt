package com.autowise.module.base.common.utils

import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import com.autowise.module.base.AppContext
import com.autowise.module.base.common.utils.DensityUtils.setDensity
import com.autowise.module.base.common.utils.ext.LogUtils.logI
import kotlin.math.min

/**
 * User: chenyun
 * Date: 2021/11/4
 * Description:
 * FIXME
 */
object DensityUtils {
    private var sNoncompatDensity = 0f
    private var sNoncompatScaledDensity = 0f


    private var density = 0f
    private var scaledDensity = 0f
    private var densityDpi = 0

    /**
     * 根据屏幕的宽度计算当前设备屏幕像素密度
     *
     * @param activity context
     * @param application application
     * @param target 目标设计图高度
     */
    fun init(application: Application, target: Float) {
        val appDisplayMetrics = application.resources
            .displayMetrics
        logI("初始density = " + appDisplayMetrics.density)
        if (sNoncompatDensity == 0f) {
            sNoncompatDensity = appDisplayMetrics.density
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.resources
                            .displayMetrics.scaledDensity
                    }
                }

                override fun onLowMemory() {}
            })
        }
        //dp 分辨率
        logI("初始宽高 = " + appDisplayMetrics.heightPixels + "  " + (appDisplayMetrics.widthPixels))
        val targetDensity =
            min(
                appDisplayMetrics.heightPixels,
                appDisplayMetrics.widthPixels
            ) / target //横竖屏问题，获取的宽高不对
        val mTargetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity)
        val targetDensityDpi = 160 * targetDensity.toInt()
        appDisplayMetrics.density = targetDensity
        appDisplayMetrics.scaledDensity = mTargetScaledDensity
        appDisplayMetrics.densityDpi = targetDensityDpi

        density = targetDensity
        scaledDensity = mTargetScaledDensity
        densityDpi = targetDensityDpi
        logI("修改后 density = " + density + " app=" + appDisplayMetrics.hashCode())
    }

    //TODO: 图片使用wrap_content时，显示出来的大小不对。
    // 似乎没有使用到我们设置的density,
    // TODO：ImageView不能使用warp_content ，需要了解imageview加载图片，设置图片大小的原理
    fun Context.setDensity() {
        if (sNoncompatDensity == 0f) return
        this.resources.displayMetrics.let {
            logI(" 修改前 density = " + it.density)
            it.density = density
            it.scaledDensity = scaledDensity
            it.densityDpi = densityDpi
            logI(" 修改后 density = " + it.density)
        }
    }


}