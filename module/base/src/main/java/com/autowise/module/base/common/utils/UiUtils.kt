package com.autowise.module.base.common.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.autowise.module.base.common.utils.DensityUtils.setDensity

object UiUtils {
    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources
            .displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources
            .displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources
            .displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources
            .displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    @Deprecated("Not work on Android 11, Use getStatusBarHeight with callback instead")
    fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId = context.resources
            .getIdentifier("status_bar_height", "dimen", "android")
        var result = 0
        result = try {
            context.resources
                .getDimensionPixelSize(resourceId)
        } catch (ex: Exception) {
            //默认status bar 给24dp的高度
            dp2px(context, 24f);
        }
        return result
    }

    /**
     * 使用setOnApplyWindowInsetsListener方法获取标题栏和导航条高度
     * @param context Activity context
     * @param requestNow 位置false，那只有等系统分发insets事件时才会有回调，一般在onCreate里为false，
     * 如果需要实时获取，设置为true。
     * 缺点：回调可能会被多次调用；回调可能会被覆盖。
     */
    fun getStatusBarHeight(
        context: Context,
        requestNow: Boolean = false,
        callback: (statusBarHeight: Int, NavigationBarHeight: Int) -> Unit
    ) {
//        context.activity?.apply {
//            ViewCompat.setOnApplyWindowInsetsListener(this.window.decorView) { _, insets ->
//                callback.invoke(insets.systemWindowInsetTop, insets.systemWindowInsetBottom)
//                insets
//            }
//            if (requestNow) {
//                ViewCompat.requestApplyInsets(this.window.decorView)
//            }
//        } ?: callback.invoke(0, 0)
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽
     */
    fun getScreenWidth(context: Context): Int {
        return context.resources
            .displayMetrics.widthPixels
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高
     */
    fun getScreenHeight(context: Context): Int {
        return context.resources
            .displayMetrics.heightPixels
    }

    /**
     * 获取宽高比
     */
    fun getHWCompare(context: Context): Double {
        val screenHeight = getScreenHeight(context).toDouble()
        val screenWidth = getScreenWidth(context).toDouble()
        return screenHeight / screenWidth
    }

    fun setTextAutoShowHide(textview: TextView, content: String?) {
        if (content.isNullOrEmpty()) {
            textview.visibility = View.GONE
        } else {
            textview.visibility = View.VISIBLE
            textview.text = content
        }
    }
}