package com.autowise.module.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import com.autowise.module.common.R

/**
 * User: chenyun
 * Date: 2022/12/13
 * Description:
 * FIXME
 */


class MaxHeightLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val TAG = "MaxHeightLinearLayout"

    var maxHeight: Float = -1f
    var minHeight: Float = -1f

    init {
        initAttr(context, attrs)
    }

    private fun initAttr(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightLinearLayout)
        maxHeight = typedArray.getDimension(R.styleable.MaxHeightLinearLayout_ll_maxHeight, -1f)
        minHeight = typedArray.getDimension(R.styleable.MaxHeightLinearLayout_ll_minHeight, -1f)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightSpec = heightMeasureSpec

        val size = MeasureSpec.getSize(heightMeasureSpec)
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        Log.i(TAG, "size= " + size + "\t maxHeight= " + maxHeight + "\t minHeight= " + minHeight)
        if (maxHeight > 0 && size > maxHeight) {
            heightSpec = MeasureSpec.makeMeasureSpec(
                maxHeight.toInt(),
                mode
            )
        }
        if (minHeight > 0 && size < minHeight) {
            heightSpec = MeasureSpec.makeMeasureSpec(
                minHeight.toInt(),
                mode
            )
        }
        super.onMeasure(widthMeasureSpec, heightSpec)
    }
}
