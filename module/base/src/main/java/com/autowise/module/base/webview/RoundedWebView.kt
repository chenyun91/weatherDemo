package com.autowise.module.base.webview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet


/**
 * User: chenyun
 * Date: 2022/1/5
 * Description:
 *   WebView 设置圆角
 */

class RoundedWebView : DWebView {
    private var mWidth = 0
    private var mHeight = 0
    private var mRadius = 0f


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    fun setRadius(radius: Float) {
        mRadius = radius
    }

    val paint = Paint()
    val path = Path()
    val recf = RectF()

    init {
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        path.fillType = Path.FillType.INVERSE_WINDING
    }

    override fun onSizeChanged(w: Int, h: Int, ow: Int, oh: Int) {
        super.onSizeChanged(w, h, ow, oh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mRadius == 0f) return
        recf.set(
            scrollX.toFloat(),
            scrollY.toFloat(),
            scrollX.toFloat() + width,
            scrollY.toFloat() + height
        )
        path.addRoundRect(recf, mRadius, mRadius, Path.Direction.CW)
        canvas?.drawPath(path, paint)
    }

}