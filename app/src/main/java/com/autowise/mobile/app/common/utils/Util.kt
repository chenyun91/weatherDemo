package com.autowise.mobile.app.common.utils

import java.util.Random

/**
 * User: chenyun
 * Date: 2021/10/22
 * Description:
 * FIXME
 */

object Util {

  /**
   * 生成随机字符串
   */
  fun getRandomString(length: Int): String {
    val charArray = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
    val sb = StringBuffer()
    val random = Random()
    for (i in 1..length) {
      sb.append(charArray[random.nextInt(charArray.size)])
    }
    return sb.toString()
  }
}