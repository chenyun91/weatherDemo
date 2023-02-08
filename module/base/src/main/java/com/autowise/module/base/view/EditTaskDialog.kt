package com.autowise.module.base.view

import android.os.Handler
import com.autowise.module.base.BaseDialogFragment
import com.autowise.module.base.R
import com.autowise.module.base.databinding.DlgEditTaskBinding
import com.autowise.module.view.ext.ViewExt.toVisible

class EditTaskDialog: BaseDialogFragment<DlgEditTaskBinding>() {

    override fun getLayoutId() = R.layout.dlg_edit_task

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
                sureListener.invoke()
                dismiss()
            }
            v.tvCancel.setOnClickListener {
                cancelListener.invoke()
                dismiss()
            }
            v.tvCancel.toVisible(cancelVisible == true)
            v.tvTitle.toVisible(!title.isNullOrEmpty())
            v.tvContent.toVisible(!content.isNullOrEmpty())
        }
    }
    fun getContent() = v.tvContent
    fun getSure() = v.tvSure
    fun getCancel() = v.tvCancel


}