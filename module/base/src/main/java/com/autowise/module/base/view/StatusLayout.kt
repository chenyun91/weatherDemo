package com.autowise.module.base.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.autowise.module.base.R
import com.autowise.module.view.ext.ViewExt.toVisible

/**
 * User: chenyun
 * Date: 2022/6/27
 * Description:
 * FIXME
 */
class StatusLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    private var errorView: ErrorView? = null
    private var noDataView: NoDataView? = null


    val titleLayoutNew: TitleLayoutNew
    val flContainer: FrameLayout

    var refreshAction: () -> Unit = {}

    init {
        LayoutInflater.from(context).inflate(R.layout.base_status_layout, this, true)
        titleLayoutNew = findViewById(R.id.title_layout)
        titleLayoutNew.ivBack.setOnClickListener {
            if (context is Activity) {
                context.onBackPressed()
            }
        }

        flContainer = findViewById(R.id.fl_container)
        titleLayoutNew.toVisible(false)

    }

    fun addToContent(
        containerView: ViewGroup,
        refreshAction: () -> Unit
    ) {
        containerView.addView(
            this, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        this.refreshAction = refreshAction
    }


    private fun setStatus(status: Status) {
        hideAllView()
        visibility = VISIBLE
        when (status) {
            Status.ERROR -> {
                if (errorView == null) {
                    errorView = ErrorView(context)
                    errorView?.addToContent(flContainer) {
                        refreshAction.invoke()
                    }
                }
                errorView?.show()
            }
            Status.NO_DATA -> {
                if (noDataView == null) {
                    noDataView = NoDataView(context)
                    noDataView?.addToContent(flContainer) {
                        refreshAction.invoke()
                    }
                }
                noDataView?.show()
            }
            Status.NONE -> visibility = GONE
        }
    }

    fun showError() {
        setStatus(Status.ERROR)
    }

    fun showNoData() {
        setStatus(Status.NO_DATA)
    }

    fun showNormal() {
        setStatus(Status.NONE)
    }

    fun hideAllView() {
        errorView?.dismiss()
        noDataView?.dismiss()
    }

}


enum class Status {
    NONE, ERROR, NO_DATA, NO_NETWORK, EMPTY
}