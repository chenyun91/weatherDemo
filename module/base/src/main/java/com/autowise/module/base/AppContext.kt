package com.autowise.module.base

import android.app.Application
import androidx.annotation.Keep
import com.autowise.module.base.common.configs.ENV
import com.autowise.module.base.common.configs.EnvConfig
import com.autowise.module.base.common.configs.EnvUtils
import com.autowise.module.base.common.utils.ext.LogUtils
import com.autowise.module.base.common.utils.ext.LogUtils.logI
import com.autowise.module.base.data.UserInfoBean
import java.util.*

/**
 * 不能混淆
 */
@Keep
object AppContext {

    @JvmStatic
    lateinit var app: Application
    lateinit var locale: Locale
    var token: String? = null
    lateinit var deviceId: String
    lateinit var env: String
    lateinit var flavor: String
    lateinit var envConfig: EnvConfig
    var isDebug = false //开启查看debug信息
    var userInfo: UserInfoBean? = null
    var uploadTrackInfo = true//是否进行错误日志上报数据
    val canDebug = true //可以显示debug面板

    //当前操作的车辆Id
    var optVehicleId: String? = null

    fun logout() {
        LogUtils.logI("AppContext", "logout")
        token = ""
        userInfo = null
    }

    val AMAP_KEY = "8549c18860655d2187f88293469e1bd4"
}