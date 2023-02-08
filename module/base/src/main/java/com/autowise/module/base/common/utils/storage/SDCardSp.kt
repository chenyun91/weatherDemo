package com.autowise.module.base.common.utils.storage

import android.os.Environment
import com.autowise.module.base.AppContext
import com.autowise.module.base.common.utils.storage.MmkvSsp

/**
 * User: chenyun
 * Date: 2022/4/21
 * Description:
 * FIXME
 */
//TODO: 读写外存
class SDCardSp : MmkvSsp(AppContext.app, "app_external_info",Environment.getExternalStorageDirectory().path) {
    override fun logout() {

    }
}