package com.autowise.module.base.common.utils.storage

import com.autowise.module.base.AppContext

/**
 * User: chenyun
 * Date: 2022/4/21
 * Description:
 * FIXME
 */
class LoginSp : MmkvSsp(AppContext.app, "login_info") {

    override fun logout() {
        putParcelable(LOGIN_BEAN, null)
        putString(TOKEN, "")
    }

    companion object {
        val USER_NAME = "login_user_name"
        val PASSWORD = "login_password"
        val REMEMBER_ME = "login_remember_me"
        val TOKEN = "login_token"

        /**
         *  包含toke以及验证信息
         */
        val LOGIN_BEAN = "login_bean"
    }
}
