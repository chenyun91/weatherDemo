package com.autowise.module.base.common.utils.ext

import android.util.Log
import com.autowise.module.base.AppContext
import com.autowise.module.base.common.utils.file.FileUtils
import com.autowise.module.base.common.utils.time.TimeFormatUtils

/**
 * User: chenyun
 * Date: 2021/12/7
 * Description:
 * FIXME
 */
object LogUtils {

    var isDebug = AppContext.isDebug

    //    val isDebug = true
    val date = TimeFormatUtils.getFormatString(System.currentTimeMillis(), "yyyy-MM-dd")

    fun Any.logI(tag: String? = null, string: String?) {
        if (isDebug) {
            Log.i(tag ?: this.javaClass.simpleName, "-- $string")
            saveInfo(tag, string)
        }
    }

    fun Any.logI(string: String?) {
        if (isDebug) {
            Log.i(this.javaClass.simpleName, "-- $string")
        }
    }

    fun logD(tag: String? = null, string: String?) {
        if (isDebug) {
            Log.d(tag ?: this.javaClass.simpleName, "-- $string")
            saveInfo(tag, string)
        }
    }

    fun Any.logE(tag: String? = null, string: String?) {
        if (isDebug) {
            Log.e(tag ?: this.javaClass.simpleName, "-- $string")
            saveInfo(tag, string)
        }
    }

    fun Any.logE(string: String?) {
        if (isDebug) {
            Log.e(this.javaClass.simpleName, "-- $string")
        }
    }

    private fun saveInfo(tag: String?, str: String?) {
//        tag?.apply {
//            FileUtils.writeFileFromString(AppContext.app, date+"logDebug.txt", tag+" : "+str + "\n")
//        }
    }
    fun saveHttpLogInfo(str: String?){
        if (isDebug){
            FileUtils.writeFileFromString(AppContext.app, date+"logDebug.txt", TimeFormatUtils.getFormatString(System.currentTimeMillis(), "HH:mm:ss")+": "+str + "\n")
        }
    }
    fun saveWsLogInfo(str: String?){
        if (isDebug){
            FileUtils.writeFileFromString(AppContext.app, date+"-wsslogDebug.txt", TimeFormatUtils.getFormatString(System.currentTimeMillis(), "HH:mm:ss")+": "+str + "\n")
        }
    }
}

