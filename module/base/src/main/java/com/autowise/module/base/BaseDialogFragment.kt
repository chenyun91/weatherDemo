package com.autowise.module.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.autowise.module.base.common.utils.DensityUtils.setDensity
import com.autowise.module.base.common.utils.LanguageUtil

/**
 *  注意：
 *  1.  contentview最外层布局宽高会被设置成warp_content，因此xml中最外层布局宽高设置会不生效，都会当成wrap_content。
 *  2.  xml中设置match_parent，不会生效。设置全屏方法：
 *          dialog?.window?.attributes?.apply {
        gravity = Gravity.CENTER//居中显示
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = this
        }

 *  3. 设置成全屏时，取消decorview的内边距,取消边距
        dialog?.window?.decorView?.setPadding(0, 0, 0, 0)

 *  4. 设置圆角背景：
        dialog?.window?.decorView?.background = ColorDrawable(Color.TRANSPARENT)

 *  5. show 不会立即提交事务显示
 *     showNow  立即提交事务显示
 *     用isResumed来判断当前dialog是否正在展示
 *
 *   6. 点击返回键弹窗区域外均不消失
 *      isCancelable=false
 *      点击弹窗区域外不消失 点击返回键消失
 *      dialog?.setCanceledOnTouchOutside(false)
 *
 *   7. 设置dialog弹出动画
 *   dialog?.window?.attributes?.windowAnimations = R.style.BottomDialogAnimation
 *
 */
abstract class BaseDialogFragment<T : ViewDataBinding> : AppCompatDialogFragment() {

    protected val TAG = this.javaClass.simpleName

    protected lateinit var v: T

    /**
     *  设置DialogFragment布局
     */
    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.setDensity()
        v = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        LanguageUtil.updateConfiguration(AppContext.app) //在重新创建任务栈的时候，由于上面的方法会导致之前语言的设置失效，重新设置一次
        initView()
        return v.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.apply {
            decorView.apply {
                background = ColorDrawable(Color.TRANSPARENT)//设置圆角时，需设置背景透明
                setPadding(0, 0, 0, 0) //取消decorview的内边距,当宽高为全屏时，许取消边距
            }
            attributes?.apply {
                gravity = Gravity.CENTER//居中显示
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
                dialog?.window?.attributes = this
            }
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    override fun dismiss() {
        if (fragmentManager == null) {
            Log.e(TAG, "dismiss: " + this + " not associated with a fragment manager.");
        } else {
            super.dismiss();
        }
    }

}