package com.autowise.module.base.common.configs

import android.os.Handler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * User: chenyun
 * Date: 2022/9/27
 * Description:
 * FIXME
 */
object LateInitBus {

    private val list = mutableListOf<() -> Unit>()

    /**
     * 3s后，执行初始化流程
     */
    fun executeInit() {
        Handler().postDelayed({
            list.forEach {
                it.invoke()
            }
        }, 3000L)
    }

    fun addInitEvent(event: () -> Unit) {
        list.add(event)
    }

}
