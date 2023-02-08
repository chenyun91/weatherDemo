package com.autowise.module.base.common.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.Log
import com.autowise.module.base.AppContext
import com.autowise.module.base.common.utils.DensityUtils.setDensity
import com.autowise.module.base.common.utils.storage.BaseSp
import com.autowise.module.base.common.utils.storage.BaseSp.Companion.SP_KEY_LANGUAGE
import java.util.*


/**
 * User: chenyun
 * Date: 2022/4/21
 * Description:
 * FIXME
 */
object LanguageUtil {

    /**
     *  application的oncreate中，调用updateConfiguration方法
     */
    fun updateConfiguration(context: Context,tag:String?="") {

        Log.d("设置前---语言",tag+" "+context.resources.configuration.locale.toLanguageTag())
        Log.d("设置前---分辨率",tag+" "+context.resources.displayMetrics.density)
        val configuration = context.resources.configuration
        configuration.setLocale(AppContext.locale)
        context.resources.updateConfiguration(
            configuration,
            context.resources.displayMetrics
        )
        context.setDensity()
        //TODO: 这里会将displayMetrics重置。
        Log.d("设置后---语言",tag+" "+context.resources.configuration.locale.toLanguageTag())
        Log.d("设置后---分辨率",tag+" "+context.resources.displayMetrics.density)
    }

    /**
     *  在activity的attachBaseContext中调用，createConfigurationContext方法
     */
    fun createConfigurationContext(context: Context): Context {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val configuration = context.resources.configuration
            configuration.setLocale(AppContext.locale)
            return context.createConfigurationContext(configuration)
        } else {
            return context;
        }
    }

    /**
     * 保存语言
     */
    fun saveLanguage(language: String) {
        BaseSp().putString(SP_KEY_LANGUAGE, language)
    }

    /**
     * 从保存在本地sp中的语言，生成对应的Local
     */
    fun getLocalFromStorage(): Locale {
        var language = BaseSp().getString(SP_KEY_LANGUAGE, "")
        if (language.isNullOrEmpty()) {
            language = Locale.getDefault().language
        }
        return when (language) {
            Locale.US.language -> {
                Locale.US
            }
            Locale.GERMANY.language -> {
                Locale.GERMANY
            }
            Locale.SIMPLIFIED_CHINESE.language -> {
                Locale.SIMPLIFIED_CHINESE
            }
            else -> Locale.US
        }
    }

    /**
     *  获取local对应的position
     */
    fun getPositionFromLocal(locale: Locale): Int {
        return when (locale.language) {
            Locale.US.language -> {
                0
            }
            Locale.GERMANY.language -> {
                1
            }
            Locale.SIMPLIFIED_CHINESE.language -> {
                2
            }
            else -> 0
        }
    }

    fun applyLanguage(locale: Locale, context: Context) {
        val resources: Resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        }
        resources.updateConfiguration(configuration, null)

        if (Build.VERSION.SDK_INT >= 25) {
            context.createConfigurationContext(configuration).apply {
                getResources().updateConfiguration(
                    configuration,
                    resources.displayMetrics
                )
            }
        }
    }

}