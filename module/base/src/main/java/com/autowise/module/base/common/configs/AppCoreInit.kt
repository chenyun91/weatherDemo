package com.autowise.module.base.common.configs

import android.app.Application
import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.autowise.module.base.AppContext
import com.autowise.module.base.BaseActivity
import com.autowise.module.base.common.utils.DensityUtils
import com.autowise.module.base.common.utils.DeviceUtils
import com.autowise.module.base.common.utils.LanguageUtil
import com.autowise.module.base.common.utils.android.ActivityLifeCycleImpl
import com.autowise.module.base.common.utils.android.ActivityStack
import com.autowise.module.base.common.utils.arouter.RouterUtils
import com.autowise.module.base.common.utils.ext.LogUtils
import com.autowise.module.base.common.utils.ext.LogUtils.logI
import com.autowise.module.base.common.utils.storage.BaseSp
import com.autowise.module.base.common.utils.storage.LoginSp
import com.autowise.module.base.common.utils.storage.LoginSp.Companion.TOKEN
import com.autowise.module.base.common.utils.storage.SpUtils
import com.autowise.module.base.data.UserInfoBean
import com.autowise.module.base.exception.CrashLogException
import com.autowise.module.base.glide.AppEngines
import com.autowise.module.base.glide.engine.impl.GlideEngine
import com.autowise.module.constant.RoutePath

/**
 * User: chenyun
 * Date: 2021/11/4
 * Description:
 * FIXME
 */
object AppCoreInit {
    var time = 0L

    var list = mutableListOf<LogoutListener>()

    fun init(app: Application, flavor: String, env: String) {
        AppContext.app = app
        EnvChange.changeEnv(env)
        AppContext.flavor = flavor
        CrashLogException.getInstance().init()//监听闪退日志
        AppContext.locale = LanguageUtil.getLocalFromStorage()
        AppContext.token = LoginSp().getString(TOKEN, "")
        //更新语言的设置
        LanguageUtil.updateConfiguration(app)
        //修改屏幕density
        DensityUtils.init(app, 360f)
        AppEngines.imageEngine = GlideEngine()
        AppContext.deviceId = DeviceUtils.getDeviceId(app) //TODO：先从data/data下获取。再从sdcard获取，最后生成。
        BaseSp().apply {
            AppContext.userInfo = getParcelable(BaseSp.USER_INFO, UserInfoBean::class.java)
            EnvChange.changeEnv(getString(BaseSp.ENV, env))
            AppContext.isDebug = getBoolean(BaseSp.DEBUG, false)
            AppContext.uploadTrackInfo = getBoolean(BaseSp.UPLOAD_TRACK_INFO, true)
            LogUtils.isDebug = AppContext.isDebug
        }
        app.registerActivityLifecycleCallbacks(ActivityLifeCycleImpl())

        ARouter.init(app); // 尽可能早，推荐在Application中初始化
        LogUtils.logI("AppCoreInit complete")
        //延迟
        LateInitBus.executeInit()
    }

    private fun initDebug() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
    }


    /**
     * 退出登录
     */
    fun logout() {
        val activity = ActivityStack.getInstance().topActivity()
        if (activity is BaseActivity<*>) {
            if (activity.isInCleanMode) return
        }
        list.forEach {
            it.logout()
        }
        SpUtils.logout()
        AppContext.logout()
        ARouter.getInstance().build(RoutePath.LOGIN_ACTIVITY)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    fun logTime(str: String) {
        val current = System.currentTimeMillis()
        time = current
    }

    /**
     * 登录
     */
    fun login() {
        RouterUtils.navigateToMain()
    }
}

interface LogoutListener {
    fun logout()
}