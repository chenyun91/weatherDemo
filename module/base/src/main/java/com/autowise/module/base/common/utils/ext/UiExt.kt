package com.autowise.module.base.common.utils.ext

import com.autowise.module.base.AppContext
import com.autowise.module.base.common.utils.UiUtils

/**
 * User: chenyun
 * Date: 2021/11/16
 * Description:
 * FIXME
 */

fun Float.dp2px(): Int {
    return UiUtils.dp2px(AppContext.app, this)
}