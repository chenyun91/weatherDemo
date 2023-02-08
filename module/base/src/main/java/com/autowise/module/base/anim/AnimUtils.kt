package com.autowise.module.base.anim

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * User: chenyun
 * Date: 2022/11/11
 * Description:
 * FIXME
 */
object AnimUtils {

    /**
     * 围绕自身旋转
     *  @param duration 动画时间
     *  @param repeatCount 动画循环次数 。 -1 动画无限循环
     */
    fun View.rotateBySelf(duration: Long = 2000, repeatCount: Int = -1): ObjectAnimator {
//        需要提前设置中心点
//        pivotX = measuredWidth / 2f
//        pivotY = measuredWidth / 2f
        val animator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = repeatCount
        animator.setDuration(duration).start()
        return animator
    }
}