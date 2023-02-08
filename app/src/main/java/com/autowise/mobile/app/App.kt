package com.autowise.mobile.app

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.autowise.module.base.common.configs.AppCoreInit

/**
 * User: chenyun
 * Date: 2021/10/15
 * Description:
 * FIXME
 */
class App : MultiDexApplication() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        AppCoreInit.init(this, BuildConfig.FLAVOR, BuildConfig.ENV_FIELD)
    }

    override fun onCreate() {
        super.onCreate()
    }
}