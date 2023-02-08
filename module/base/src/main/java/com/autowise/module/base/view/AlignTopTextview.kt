package com.autowise.module.base.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * User: chenyun
 * Date: 2022/8/23
 * Description:
 *  设置drawableLeft时，
 *  图标与文字顶部对齐的Textview
 * 参考： https://juejin.cn/post/6975449419571265543
 * FIXME
 */
class AlignTopTextview @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        handleLeftDrawable();
    }

    private fun handleLeftDrawable() {
        val leftDrawable = compoundDrawables[0] ?: return //TextView.Drawables.LEFT
        //获取实际行数
        val lineCount = lineCount.coerceAtMost(maxLines)
        //获取文本高度
        val vsPace = bottom - top - compoundPaddingBottom - compoundPaddingTop
        //计算位置差值
        val verticalOffset = (-1 * (vsPace * (1 - 1.0f / lineCount)) / 2).toInt()
        //重新设置Bounds
        leftDrawable.setBounds(
            0, verticalOffset, leftDrawable.intrinsicWidth,
            leftDrawable.intrinsicHeight + verticalOffset
        )
    }
}
