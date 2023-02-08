package com.autowise.module.base.common.utils.storage

import com.autowise.module.base.AppContext
import com.autowise.module.base.common.utils.storage.MmkvSsp

/**
 * User: chenyun
 * Date: 2022/4/21
 * Description:
 * FIXME
 */
class BaseSp : MmkvSsp(AppContext.app, "app_info") {
    companion object {
        const val SP_KEY_LANGUAGE = "language"
        const val USER_INFO = "user_info"

        /**
         * app环境
         */
        const val ENV = "env"

        /**
         * debug
         */
        const val DEBUG = "debug"

        /**
         * 上传日志
         */
        const val UPLOAD_TRACK_INFO = "uploadTrackInfo"

        /**
         * 上一次显示通知权限提醒的时间， 超过7天提醒一次
         */
        const val LAST_SHOW_PERMISSION_TIME = "last_show_permission_time"

        /**
         * 是否显示用户协议&隐私政策
         */
        const val SHOW_PRIVACY = "show_privacy"

    }

    override fun logout() {
        putParcelable(USER_INFO, null)
    }
}