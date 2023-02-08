package com.autowise.module.base.common.utils.ext

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.*
import androidx.core.animation.addListener

fun View.alphaIn(
    duration: Long = 750,
    startDelay: Long = 0,
    from: Float = 0f,
    to: Float = 1f,
    action: () -> Unit = {}
): ObjectAnimator =
    ObjectAnimator.ofFloat(this, "alpha", from, to).apply {
        this.duration = duration
        this.startDelay = startDelay
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                action.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }

fun View.alphaOut(
    duration: Long = 750,
    startDelay: Long = 0,
    from: Float = 1f,
    to: Float = 0f,
    action: () -> Unit = {}
): ObjectAnimator =
    ObjectAnimator.ofFloat(this, "alpha", from, to).apply {
        this.duration = duration
        this.startDelay = startDelay
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                action.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }

fun View.anim(
    duration: Long = 750,
    startDelay: Long = 0,
    propertyName: String,
    vararg values: Float,
    action: () -> Unit = {}
    ): ObjectAnimator =
    ObjectAnimator.ofFloat(this, propertyName, *values).apply {
        this.duration = duration
        this.startDelay = startDelay
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                action.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }

fun View.translationY(
    duration: Long = 250,
    startDelay: Long = 0,
    from: Float = 30f,
    to: Float = 0f,
    after: () -> Unit = {},
    before: () -> Unit = {},
): ObjectAnimator =
    ObjectAnimator.ofFloat(this, "translationY", from, to).apply {
        this.duration = duration
        this.startDelay = startDelay
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                before.invoke()
            }

            override fun onAnimationEnd(animation: Animator?) {
                after.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }

fun View.translationX(
    duration: Long = 750,
    startDelay: Long = 0,
    from: Float = 60f,
    to: Float = 0f,
    action: () -> Unit = {}
): ObjectAnimator =
    ObjectAnimator.ofFloat(this, "translationX", from, to).apply {
        this.duration = duration
        this.startDelay = startDelay
        interpolator = AccelerateDecelerateInterpolator()
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                action.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }


fun View.scaleX(
    duration: Long = 300,
    from: Float = 1f,
    to: Float = 1.25f,
    action: () -> Unit = {}
): ObjectAnimator = ObjectAnimator.ofFloat(this, "scaleX", from, to).apply {
    this.duration = duration
    this.startDelay = startDelay
    interpolator = AccelerateDecelerateInterpolator()
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            action.invoke()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationRepeat(animation: Animator?) {
        }

    })
}

fun View.scaleY(
    duration: Long = 300,
    from: Float = 1f,
    to: Float = 1.25f,
    action: () -> Unit = {}
): ObjectAnimator = ObjectAnimator.ofFloat(this, "scaleY", from, to).apply {
    this.duration = duration
    this.startDelay = startDelay
    interpolator = AccelerateDecelerateInterpolator()
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            action.invoke()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationRepeat(animation: Animator?) {
        }

    })
}


fun View.scaleLarge(
    mDuration: Long = 300,
    from: Float = 1f,
    to: Float = 1.1f,
    action: () -> Unit = {}
): AnimatorSet {
    val scaleX = ObjectAnimator.ofFloat(this, "scaleX", from, to)
    val scaleY = ObjectAnimator.ofFloat(this, "scaleY", from, to)
    return AnimatorSet().apply {
        duration = mDuration
        interpolator = OvershootInterpolator()
        play(scaleX).with(scaleY)
        addListener(onEnd = {
            action.invoke()
        })
    }
}

fun View.scaleNormal(
    mDuration: Long = 300,
    from: Float = 1.1f,
    to: Float = 1f,
    action: () -> Unit = {}
): AnimatorSet {
    val scaleX = ObjectAnimator.ofFloat(this, "scaleX", from, to)
    val scaleY = ObjectAnimator.ofFloat(this, "scaleY", from, to)
    return AnimatorSet().apply {
        duration = mDuration
        interpolator = OvershootInterpolator()
        play(scaleX).with(scaleY)
        addListener(onEnd = {
            action.invoke()
        })
    }
}


/**
 * 底部进入的动画
 */
fun View.bottomAlphaIn(onEnd: () -> Unit = {}) {
    AnimatorSet().apply {
        play(alphaIn(duration = 250) {

        }).with(
            translationY(
                duration = 400,
                from = measuredHeight.toFloat(),
                to = 0f
            ) { })
        addListener(
            onEnd = {
                onEnd()
            }
        )
        start()
    }
}

/**
 * 底部退出的动画
 */
fun View.bottomAlphaOut(onEnd: () -> Unit = {}) {
    AnimatorSet().apply {
        play(alphaOut(duration = 150, to = 1f) {
            alphaOut(duration = 250)
        }).with(
            translationY(
                duration = 400,
                from = 0f,
                to = measuredHeight.toFloat()
            )
        )
        addListener(
            onEnd = {
                onEnd()
            }
        )
        start()
    }


}