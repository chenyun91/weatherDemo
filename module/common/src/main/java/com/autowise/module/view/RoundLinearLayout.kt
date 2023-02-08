package com.autowise.module.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import com.autowise.module.common.R

/**
 * User: chenyun
 * Date: 2022/12/4
 * Description: 圆角的linearlayout
 * FIXME
 */
class RoundLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var radius = 0f
    var topLeftRadius = 0f
    var topRightRadius = 0f
    var bottomLeftRadius = 0f
    var bottomRightRadius = 0f
    var isOval = false //是否是圆形
    var strokeColor = -1
    var strokeWidth = 0f

    var paint: Paint? = null

    init {
        initAttr(context, attrs)
        if (strokeWidth > 0) {
            paint = Paint().apply {
                strokeWidth = strokeWidth
                color = strokeColor
                style = Paint.Style.STROKE
                isAntiAlias = true
            }
        }
    }

    private fun initAttr(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundLinearLayout)
        isOval = typedArray.getBoolean(R.styleable.RoundLinearLayout_rllIsOval, false)
        radius = typedArray.getDimension(R.styleable.RoundLinearLayout_rllRadius, 0f)
        if (!isOval && radius == 0f) {
            topLeftRadius =
                typedArray.getDimension(R.styleable.RoundLinearLayout_rllTopLeftRadius, 0f)
            topRightRadius =
                typedArray.getDimension(R.styleable.RoundLinearLayout_rllTopRightRadius, 0f)
            bottomLeftRadius =
                typedArray.getDimension(R.styleable.RoundLinearLayout_rllBottomLeftRadius, 0f)
            bottomRightRadius =
                typedArray.getDimension(R.styleable.RoundLinearLayout_rllBottomRightRadius, 0f)
        }
        strokeColor =
            typedArray.getColor(R.styleable.RoundLinearLayout_rllStrokeColor, Color.TRANSPARENT)
        strokeWidth = typedArray.getDimension(R.styleable.RoundLinearLayout_rllStrokeWidth, 0f)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        drawRect(canvas, false)
        super.onDraw(canvas)
    }


    override fun dispatchDraw(canvas: Canvas?) {
        drawRect(canvas, true)
        super.dispatchDraw(canvas)
    }

    private fun drawRect(canvas: Canvas?, isDispatchDraw: Boolean) {
        val path = Path()
        val w = width.toFloat()
        val h = height.toFloat()
        val rectF = RectF(0f, 0f, w, h)
        if (isOval) {
            path.addRoundRect(
                rectF,
                w / 2f,
                h / 2f,
                Path.Direction.CW
            )
        } else if (radius > 0) {
            path.addRoundRect(
                rectF,
                radius,
                radius,
                Path.Direction.CW
            )
        } else {
            path.addRoundRect(
                rectF,
                floatArrayOf(
                    topLeftRadius,
                    topLeftRadius,
                    topRightRadius,
                    topRightRadius,
                    bottomLeftRadius,
                    bottomLeftRadius,
                    bottomRightRadius,
                    bottomRightRadius
                ),
                Path.Direction.CW
            )
        }
        canvas?.clipPath(path)
        paint?.apply {
            canvas?.drawPath(path, this)
        }
        if (background != null && width > 0 && height > 0) {
            if (background is ColorDrawable) {
                val colorDrawable = background as ColorDrawable
                val paint = Paint()
                paint.color = colorDrawable.color
                paint.isAntiAlias = true
                canvas?.drawPath(path, paint)
            }
        }
    }


}