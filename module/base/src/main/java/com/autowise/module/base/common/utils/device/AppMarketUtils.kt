package com.autowise.module.base.common.utils.device

import android.content.Context
import android.content.Intent
import android.net.Uri


/**
 * User: chenyun
 * Date: 2022/7/13
 * Description: https://www.jianshu.com/p/b469ce244444
 * 跳转到应用市场
 * FIXME
 */
object AppMarketUtils {


    fun Context.jumpToMarket() {
        //存在手机里没安装应用市场的情况，跳转会包异常，做一个接收判断
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=$packageName")

        if (intent.resolveActivity(packageManager) != null) {
            //可以接收
            startActivity(intent)
        } else {
            //没有应用市场，我们通过浏览器跳转到Google Play
            intent.data =
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")

            //这里存在一个极端情况就是有些用户浏览器也没有，再判断一次
            if (intent.resolveActivity(packageManager) != null) {
                //有浏览器
                startActivity(intent)
            }
        }
    }

}