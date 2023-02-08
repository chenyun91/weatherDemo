package com.autowise.module.base.view

/**
 * 无法被关闭的dialog
 */
class CanNotCancelDialog : ConfirmDialog() {


    override fun initView() {
        isCancelable = false
    }

    override fun dismiss() {

    }
}