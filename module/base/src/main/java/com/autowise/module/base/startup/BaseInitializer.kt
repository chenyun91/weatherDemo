package com.autowise.module.base.startup

import android.content.Context
import androidx.startup.Initializer
import com.autowise.module.base.common.configs.LateInitBus

/**
 * User: chenyun
 * Date: 2022/12/12
 * Description:
 * FIXME
 */
abstract class BaseInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        LateInitBus.addInitEvent {
            addLateInitEvent()
        }
    }

    abstract fun addLateInitEvent()

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}