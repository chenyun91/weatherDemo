package com.autowise.module.base.common.utils

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.autowise.module.base.AppContext
import com.autowise.module.base.R

object ToastUtils {


    fun showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (message.isNullOrEmpty()) return
        showToastImpl(message, duration)
    }

    private fun showToastImpl(
        message: String,
        duration: Int
    ) {
        showCustomToast(
            message,
            duration,
            getColor(R.color.color_253746),
            R.drawable.base_bg_toast_normal
        )
    }

    fun showErrorToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        showCustomToast(
            message,
            duration,
            getColor(R.color.base_toast_color_red),
            R.drawable.base_bg_toast_red
        )
    }

    fun showSuccessToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        showCustomToast(
            message,
            duration,
            getColor(R.color.base_toast_color_green),
            R.drawable.base_bg_toast_green
        )
    }

    private fun showCustomToast(message: String, duration: Int, colorId: Int, backgroundId: Int) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Handler(Looper.getMainLooper()).post {
                showToastView(message, duration, colorId, backgroundId)
            }
        } else {
            showToastView(message, duration, colorId, backgroundId)
        }

    }

    private fun showToastView(message: String, duration: Int, colorId: Int, backgroundId: Int) {
        val view = LayoutInflater.from(AppContext.app).inflate(R.layout.base_toast_view, null)
        view.findViewById<TextView>(R.id.tv_txt).apply {
            text = message
            setTextColor(colorId)
            setBackgroundResource(backgroundId)
        }
        Toast(AppContext.app).apply {
            setDuration(duration)
            setView(view)
            show()
        }
    }

    private fun getColor(colorId: Int): Int {
        return AppContext.app.resources.getColor(colorId)
    }
}