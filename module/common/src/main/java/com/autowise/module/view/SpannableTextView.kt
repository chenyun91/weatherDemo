package com.autowise.module.view

import android.R
import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.max

/**
 * User: chenyun
 * Date: 2022/6/24
 * Description: 可以部分点击的Textview
 * FIXME
 */
class SpannableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {


    /**
     *  我已阅读aaa和bbb
     * @param originText 初始文本，带%s
     * @param clickTexts 需要点击的文本内容
     */
    fun setSpanView(
        originText: String,
        vararg clickTexts: String,
        clickColor: Int,
        clickListener: (Int) -> Unit = {}
    ) {
        val result = String.format(originText, *clickTexts)
        val sb = SpannableStringBuilder(result)
        for (i in clickTexts.indices) {
            val index = result.indexOf(clickTexts[i])
            sb.setSpan(
                ForegroundColorSpan(resources.getColor(clickColor)),
                index,
                index + clickTexts[i].length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            sb.setSpan(
                getClickableSpan(i, clickColor, clickListener),
                index,
                index + clickTexts[i].length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

        }
        text = sb
        movementMethod = LinkMovementMethod.getInstance()
    }

    fun getClickableSpan(position: Int, color: Int, clickListener: (Int) -> Unit): ClickableSpan {
        return object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                highlightColor = resources.getColor(R.color.transparent) //背景色
                ds.color = resources.getColor(color)
                ds.isUnderlineText = false
                ds.clearShadowLayer()
            }

            override fun onClick(widget: View) {
                clickListener(position)
            }
        }
    }

}