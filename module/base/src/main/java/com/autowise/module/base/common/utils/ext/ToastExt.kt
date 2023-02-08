package com.autowise.module.base.common.utils.ext

/**
 * User: chenyun
 * Date: 2021/11/15
 * Description:
 * FIXME
 */

import android.app.Activity
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.autowise.module.base.common.utils.ToastUtils


fun Activity.showToast(msg: String?) {
    ToastUtils.showToast(msg)
}

fun Fragment.showToast(msg: String?) {
    this.activity?.apply {
        ToastUtils.showToast(msg)
    }
}

fun Activity.showToast(@StringRes msgRes: Int) {
    ToastUtils.showToast(getString(msgRes))
}

fun Fragment.showToast(@StringRes msgRes: Int) {
    this.activity?.apply {
        ToastUtils.showToast(getString(msgRes))
    }
}

fun ViewModel.showToast(msg: String?) {
    ToastUtils.showToast(msg)
}