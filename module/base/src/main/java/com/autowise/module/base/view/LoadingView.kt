package com.autowise.module.base.view

import android.content.Context
import android.opengl.Visibility
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.autowise.module.base.R
import com.autowise.module.base.common.utils.ToastUtils
import com.autowise.module.view.ext.ViewExt.toVisible
import com.bumptech.glide.Glide

/**
 * User: chenyun
 * Date: 2022/1/7
 * Description:
 * FIXME
 */
class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    var cancelable = true //loading默认可以返回键隐藏

    init {
        LayoutInflater.from(context).inflate(R.layout.base_loading_layout, this, true)
        findViewById<ImageView>(R.id.iv_image)?.apply {
            Glide.with(this).load(R.drawable.bg_loading).into(this)
        }
        setOnClickListener {

        }
    }

    fun addToContent(containerView: ViewGroup) {
        containerView.addView(
            this, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    fun show(cancelable: Boolean) {
        this.cancelable = cancelable
        toVisible(true)
    }

    fun dismiss() {
        toVisible(false)
    }

    fun isShowing(): Boolean {
        return View.VISIBLE == visibility
    }

}