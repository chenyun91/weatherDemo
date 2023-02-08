package com.autowise.module.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.autowise.module.base.R
import com.autowise.module.view.ext.ViewExt.toVisible

/**
 * User: chenyun
 * Date: 2022/6/27
 * Description:
 * FIXME
 */
class NoDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    val tvRefresh: TextView

    var refreshAction: () -> Unit = {}

    init {
        LayoutInflater.from(context).inflate(R.layout.base_nodata_layout, this, true)
        tvRefresh = findViewById(R.id.tv_refresh)
        tvRefresh.setOnClickListener {
            refreshAction()
        }
    }

    fun addToContent(containerView: ViewGroup, refreshAction: () -> Unit) {
        containerView.addView(
            this, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        this.refreshAction = refreshAction
    }

    fun show() {
        toVisible(true)
    }

    fun dismiss() {
        toVisible(false)
    }

}