package com.autowise.module.base.common.utils.number

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * User: chenyun
 * Date: 2022/8/26
 * Description: https://www.jianshu.com/p/d3c1a59e6bd1
 * FIXME
 */

object NumberUtils {

    /**
     * 去掉小数点后，多余的0
     * @param number "800.00"
     */
    fun stripTrailingZeros(number: String): String {
        return BigDecimal(number).stripTrailingZeros().toPlainString()
    }

    /**
     *  格式化，保留几位小数
     * @param number "800.00"
     */
    fun formatNumber(pattern: String, number: Any): String {
        return DecimalFormat(pattern).format(number)
    }

    /**
     *  默认四舍五，
     *  默认保留一位小数
     *  @param newScale 保留newScale位小数
     *  @param mode
     *      RoundingMode.HALF_UP 四舍五入
     *      RoundingMode.HALF_UP 四舍五入
     */
    fun formatNumber(
        number: Double,
        newScale: Int = 1,
        mode: RoundingMode = RoundingMode.HALF_UP,
        stripTrailingZeros: Boolean = true
    ): String {
        var scale = BigDecimal(number).setScale(newScale, mode)
        if (stripTrailingZeros) {
            scale = scale.stripTrailingZeros()
        }
        return scale.toPlainString()
    }


}