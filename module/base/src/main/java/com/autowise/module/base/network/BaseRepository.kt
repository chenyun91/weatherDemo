package com.autowise.module.base.network

import com.autowise.module.base.common.configs.EnvChange

/**
 * User: chenyun
 * Date: 2022/12/22
 * Description:
 * FIXME
 */
abstract class BaseRepository<T> {
    var commonApi = createApi()

    abstract fun createApi(): T

    init {
        EnvChange.addEnvChangeEvent {
            commonApi = createApi()
            this::class.simpleName.toString()
        }
    }

}