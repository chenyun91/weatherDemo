package com.autowise.module.base.network

import com.autowise.module.base.AppContext
import com.autowise.module.base.BaseActivity
import com.autowise.module.base.R
import com.autowise.module.base.common.configs.AppCoreInit
import com.autowise.module.base.common.configs.ErrorCode.FORCE_UPGRADE
import com.autowise.module.base.common.configs.ErrorCode.SHOW_ERROR_DIALOG
import com.autowise.module.base.common.configs.ErrorCode.TokenExpired
import com.autowise.module.base.common.utils.ToastUtils
import com.autowise.module.base.common.utils.android.ActivityStack
import com.autowise.module.base.common.utils.device.AppMarketUtils.jumpToMarket
import com.autowise.module.base.view.CanNotCancelDialog
import com.autowise.module.base.view.ConfirmDialog

/**
 * User: chenyun
 * Date: 2022/7/13
 * Description:  统一处理后台返回的错误码
 * FIXME
 */
object HandleError {

    var showForceUpgradeDialog = false

    fun handle(t: BaseResponse<*>): Boolean {
        var result = true
        when (t.code) {
            FORCE_UPGRADE -> { //强制升级
                handleForceUpgrade()
            }
            TokenExpired -> {//token过期
                ToastUtils.showToast(AppContext.app.getString(R.string.common_login_expired))
                AppCoreInit.logout()
            }
            SHOW_ERROR_DIALOG -> { //将错误信息弹窗显示
                showErrorDialog(t.message)
            }
            else -> {
                result = false
            }
        }
        return result
    }


    /**
     * 强制升级
     */
    fun handleForceUpgrade() {
        if (!showForceUpgradeDialog) {
            showForceUpgradeDialog = true
            val activity = ActivityStack.getInstance().topActivity()
            if (activity is BaseActivity<*>) {
                if (activity.isInCleanMode) return
                val dialog = CanNotCancelDialog()
                dialog.show(activity.supportFragmentManager, "ForceCloseDialog")
                dialog.setData(
                    activity.getString(R.string.version_not_supported_title),
                    activity.getString(R.string.version_not_supported_content),
                    activity.getString(R.string.version_upgrade),
                    null,
                    sureListener = {
                        activity.jumpToMarket()
                    },
                    { },
                    false
                )
            }
        }
    }

    /**
     * 将错误信息弹窗显示
     */
    private fun showErrorDialog(message: String?) {
        if (message.isNullOrEmpty()) return
        val activity = ActivityStack.getInstance().topActivity()
        if (activity is BaseActivity<*>) {
            if (activity.isInCleanMode) return
            val dialog = ConfirmDialog()
            dialog.show(activity.supportFragmentManager, "showErrorDialog")
            dialog.setData(
                message,
                null,
                activity.getString(R.string.common_ok),
                null,
                {},
                { },
                false
            )
        }
    }
}