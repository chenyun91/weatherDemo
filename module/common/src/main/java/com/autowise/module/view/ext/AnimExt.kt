package com.autowise.module.view.ext

import android.animation.ObjectAnimator

import android.animation.PropertyValuesHolder
import android.view.View


/**
 * User: chenyun
 * Date: 2022/4/29
 * Description:
 * FIXME
 */
object AnimExt {


    /**
     * 左右抖动动画
     */
    fun View.shake() {
        val pvhX = PropertyValuesHolder.ofFloat("translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
        ObjectAnimator.ofPropertyValuesHolder(this, pvhX).setDuration(300).start()
    }
}