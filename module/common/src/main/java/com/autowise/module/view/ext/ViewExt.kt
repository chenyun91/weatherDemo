package com.autowise.module.view.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.autowise.module.view.ext.ViewExt.getDrawable

/**
 * User: chenyun
 * Date: 2021/12/20
 * Description:
 * FIXME
 */
object ViewExt {
    private fun View.toVisible() {
        this.visibility = View.VISIBLE
    }

    private fun View.toGone() {
        this.visibility = View.GONE
    }

    private fun View.toInVisible() {
        this.visibility = View.INVISIBLE
    }

    fun View.toVisible(boolean: Boolean) {
        if (boolean) toVisible() else toGone()
    }

    fun View.toInVisible(boolean: Boolean) {
        if (boolean) toInVisible() else toVisible()
    }

    fun View.scale(float: Float, originWidth: Int?, originHeight: Int?) {
        layoutParams = layoutParams.apply {
            originWidth?.apply {
                width = (float * this).toInt()
            }
            originHeight?.apply {
                height = (float * this).toInt()
            }
        }
    }


    /**
     * 代码修改drawable属性
     * @param solidColor solid color
     * @param strokeWidth stroke width
     * @param strokeColor stroke color
     * @param dashWidth dash
     * @param dashGap gap
     */
    fun View.setGradientDrawable(
        @ColorInt solidColor: Int,
        strokeWidth: Int = 0,
        @ColorInt strokeColor: Int = -1,
        dashWidth: Float = 0f,
        dashGap: Float = 0f
    ) {
        val gd = background.current as GradientDrawable
        if (solidColor>0){
            gd.setColor(solidColor)
        }
        if (strokeWidth > 0) {
            gd.setStroke(strokeWidth, strokeColor, dashWidth, dashGap)
        }
    }

    /**
     * 修改svg图片的颜色
     * @param iv            imageview
     * @param svgImageColor 修改后的颜色
     * @param svgImage      修改的svg图片
     *
     *  代码中设置：
     *  @ColorInt  eg:  ContextCompat.getColor(requireContext(),R.color.color_4098FF)
     *
     *  @DrawableRes  eg: R.drawable.svg_hardware.monitor
     *  @ColorRes  eg： R.color.color_4098FF
     *
     *  从xml中读取：
     *
     */
    fun ImageView.setSVGImageColor(
        @ColorInt svgImageColor: Int,
        @DrawableRes svgImage: Int
    ) {
        val vectorDrawableCompat =
            VectorDrawableCompat.create(context.resources, svgImage, context.theme)
                ?: return
        vectorDrawableCompat.setTint(svgImageColor)
        setImageDrawable(vectorDrawableCompat)
    }

    fun Context.getColorInt(@ColorRes colorRes: Int): Int {
        return resources.getColor(colorRes, theme)
    }

    fun Context.getDrawable(@DrawableRes drawableRes: Int): Drawable? {
        return ResourcesCompat.getDrawable(resources, drawableRes, theme)
    }

}