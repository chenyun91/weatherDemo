package com.autowise.module.base.view

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.autowise.module.base.R
import com.autowise.module.base.common.utils.StatusBarUtils
import com.autowise.module.view.ext.ViewExt.toInVisible
import com.autowise.module.view.ext.ViewExt.toVisible

/**
 * User: chenyun
 * Date: 2022/4/28
 * Description: 统一的标题栏
 * FIXME
 */
class TitleLayoutNew @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    val tvTitle: TextView
    val ivBack: ImageView
    val ivRight: ImageView
    val viewLine: View

    /**
     *  点击左边边返回图标的事件监听
     */
    var onBackClickListener: () -> Boolean = { false }

    /**
     *  点击右边图标的事件监听
     */
    var onRightClickListener: () -> Unit = { false }

    var titleLayoutHeight: Int = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.base_title_layout, this, true)
        ivBack = findViewById(R.id.iv_back)
        ivRight = findViewById(R.id.iv_right)
        tvTitle = findViewById(R.id.tv_title)
        viewLine = findViewById(R.id.view_line)
        ivBack.setOnClickListener {
            if (onBackClickListener.invoke()) {
                return@setOnClickListener
            } else {
                if (context is Activity) {
                    context.finish()
                }
            }
        }
        ivRight.setOnClickListener {
            onRightClickListener()
        }
        initAttr(context, attrs)

        setPadding(0, StatusBarUtils.getStatusBarHeight(context), 0, 0)

        post { titleLayoutHeight = measuredHeight }
    }


    private fun initAttr(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout)
        val title = typedArray.getString(R.styleable.TitleLayout_title)
        val backVisible = typedArray.getBoolean(R.styleable.TitleLayout_backVisible, true)
        val ivRightVisible = typedArray.getBoolean(R.styleable.TitleLayout_ivRightVisible, false)
        val rightSrc = typedArray.getDrawable(R.styleable.TitleLayout_rightSrc)
        val lineVisible = typedArray.getBoolean(R.styleable.TitleLayout_lineVisible, true)
        val backGroundColor =
            typedArray.getColor(
                R.styleable.TitleLayout_backgroundColor,
                context.resources.getColor(R.color.color_app_title)
            )
        setTitleBackgroundColor(backGroundColor)
        typedArray.recycle()
        setTitle(title ?: "")
        ivBack.toInVisible(!backVisible)
        setIvRightVisible(ivRightVisible)
        ivRight.setImageDrawable(rightSrc)
        setLineVisible(lineVisible)
    }

    fun setTitleBackgroundColor(@ColorInt color: Int) {
        setBackgroundColor(color)
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun setIvRightVisible(ivRightVisible: Boolean) {
        ivRight.toInVisible(!ivRightVisible)
    }

    fun setLineVisible(lineVisible: Boolean) {
        viewLine.toVisible(lineVisible)
    }

}
