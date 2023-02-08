package com.autowise.module.base.common.configs

import com.autowise.module.base.AppContext
import com.autowise.module.base.common.utils.ext.LogUtils
import com.autowise.module.base.common.utils.ext.LogUtils.logI
import com.autowise.module.base.common.utils.storage.BaseSp

/**
 * User: chenyun
 * Date: 2022/12/22
 * Description:
 * FIXME
 */
object EnvChange {
    private val list = mutableListOf<() -> String>()

    /**
     * 修改环境
     */
    fun changeEnv(env: String) {
        AppContext.env = env
        AppContext.envConfig = EnvUtils.getConfig(env)
        notifyEnvChange()
    }

    fun addEnvChangeEvent(event: () -> String) {
        list.add(event)
    }

    fun notifyEnvChange() {
        list.forEach {
            var it1 = it()
            LogUtils.logI("修改环境", "------" + it1)
        }
    }
}