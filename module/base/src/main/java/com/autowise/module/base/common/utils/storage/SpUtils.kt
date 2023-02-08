package com.autowise.module.base.common.utils.storage

/**
 * User: chenyun
 * Date: 2022/6/14
 * Description:
 * FIXME
 */
object SpUtils {
    fun logout(){
        LoginSp().logout()
        BaseSp().logout()
    }
}