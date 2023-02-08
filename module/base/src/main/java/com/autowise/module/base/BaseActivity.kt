package com.autowise.module.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.autowise.module.base.common.utils.DensityUtils.setDensity
import com.autowise.module.base.common.utils.LanguageUtil
import com.autowise.module.base.common.utils.StatusBarUtils
import com.autowise.module.base.common.utils.ext.LogUtils.logD
import com.autowise.module.base.common.utils.ext.LogUtils.logI
import com.autowise.module.base.view.ConfirmDialog
import com.autowise.module.base.view.LoadingView
import com.autowise.module.base.view.StatusLayout

/**
 * User: chenyun
 * Date: 2021/10/13
 * Description:
 * FIXME
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    protected val TAG = "Lifecycle_" + this.javaClass.simpleName

    protected lateinit var v: T


    //todo：消息会丢失
    var isInCleanMode = false // true， 不显示设备验证弹窗 和强行退出退出登录操作

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageUtil.createConfigurationContext(newBase))
        logI(TAG, "attachBaseContext")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logI(TAG, "onCreate")
        createView()
    }

    fun createView() {
//        setDensity()
        LanguageUtil.updateConfiguration(AppContext.app,"application")
        LanguageUtil.updateConfiguration(this,"activity")
        getIntentData()
        v = DataBindingUtil.setContentView(this, getLayoutId())
        initViewModel()?.apply {
            setViewModel(this)//TODO: 不够好
        }
        initView()
        setStatusBar()
        refreshData()
    }

    /**
     * 设置沉浸式状态栏
     */
    open fun setStatusBar() {
        //状态栏高度 97px 35dp
        StatusBarUtils.setNavigationStatusBar(this,true)
    }

    /**
     * 设置viewModel，实现显示loadding效果
     */
    fun setViewModel(vm: BaseVM?) {
        vm?.isShowLoading?.observe(this) {
            if (it.isShowLoading) {
                showLoading(it.cancelable)
            } else {
                dismissLoading()
            }
        }
        vm?.isShowError?.observe(this) {
            if (it == true) {
                showError()
            } else {
                showNormal()
            }
        }
    }

    abstract fun getLayoutId(): Int

    open fun getIntentData() {}

    open fun initViewModel(): BaseVM? {
        return null
    }

    abstract fun initView()

    fun showConfirmDialog(title: String?, sureListener: () -> Unit) {
        showConfirmDialog(title, null, null,null,sureListener, {},null)
    }

    fun showConfirmDialog(
        title: String? = null,
        content: String? = null,
        sureTxt: String? = null,
        cancelTxt: String? = null,
        sureListener: () -> Unit?,
        cancelListener: () -> Unit? = {},
        cancelVisible: Boolean? = true,
    ) {
        val dialog = ConfirmDialog()
        dialog.show(supportFragmentManager, "ConfirmDialog")
        dialog.setData(
            title,
            content,
            sureTxt,
            cancelTxt,
            sureListener,
            cancelListener,
            cancelVisible
        )
    }

    /******************显示Loading页面******************************/
    var loadingView: LoadingView? = null

    fun showLoading(cancelable: Boolean = true) {
        logD(TAG,"BaseActivity---> showLoading")
        if (loadingView == null) {
            loadingView = LoadingView(this)
            loadingView?.addToContent(findViewById(android.R.id.content))
            loadingView?.bringToFront() //显示在最顶层
        }
        loadingView?.show(cancelable)
    }

    fun dismissLoading() {
        logD(TAG,"BaseActivity---> dismissLoading")
        loadingView?.dismiss()
    }


    /******************显示错误页面******************************/
    /**
     * 显示error、nodata等状态的布局
     * 不显示标题
     * 默认的刷新操作为refreshData()
     */
    private var statusLayout: StatusLayout? = null

    /**
     * 指定状态显示的父容器
     */
    fun setContainerView(viewGroup: ViewGroup) {
        statusLayout = StatusLayout(this).apply {
            addToContent(
                viewGroup,
                this@BaseActivity::refreshData
            )
        }
    }


    fun showError() {
        statusLayout?.showError()
    }


    fun showNoData() {
        statusLayout?.showNoData()
    }

    fun showNormal() {
        statusLayout?.showNormal()
    }


    open fun refreshData() {
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logI(TAG, "onNewIntent")
    }

    override fun onBackPressed() {
        if (loadingView?.isShowing() == true) {
            if (loadingView?.cancelable == true) {
                loadingView?.dismiss()
            }
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        logI(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        logI(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        logD(TAG, "onDestroy")
    }
}