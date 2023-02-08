package com.autowise.module.base.view

import android.os.Handler
import com.autowise.module.base.BaseDialogFragment
import com.autowise.module.base.R
import com.autowise.module.base.databinding.BaseConfirmDialogBinding
import com.autowise.module.view.ext.ViewExt.toVisible

open class ConfirmDialog : BaseDialogFragment<BaseConfirmDialogBinding>() {

    override fun getLayoutId() = R.layout.base_confirm_dialog

    override fun initView() {

    }

    fun setData(
        title: String? = null,
        content: String? = null,
        sureTxt: String? = null,
        cancelTxt: String? = null,
        sureListener: () -> Unit? = {},
        cancelListener: () -> Unit? = {},
        cancelVisible: Boolean? = true,
        extraAction: () -> Unit = {},
        cancelable: Boolean = true
    ) {
        Handler().post {
            content?.apply {
                v.tvContent.text = this
            }
            title?.apply {
                v.tvTitle.text = this
            }
            sureTxt?.apply {
                v.tvSure.text = this
            }
            cancelTxt?.apply {
                v.tvCancel.text = this
            }
            v.tvSure.setOnClickListener {
                dismiss()
                sureListener.invoke()
            }
            v.tvCancel.setOnClickListener {
                dismiss()
                cancelListener.invoke()
            }
            v.tvCancel.toVisible(cancelVisible == true)
            v.tvTitle.toVisible(!title.isNullOrEmpty())
            v.tvContent.toVisible(!content.isNullOrEmpty())
            extraAction.invoke()
        }
        isCancelable = cancelable
    }
    fun getContent() = v.tvContent
    fun getTitle() = v.tvTitle
    fun getSure() = v.tvSure
    fun getCancel() = v.tvCancel


}