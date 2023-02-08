package com.autowise.module.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.autowise.module.base.common.utils.DensityUtils.setDensity
import com.autowise.module.base.common.utils.LanguageUtil
import com.autowise.module.base.common.utils.ext.LogUtils
import com.autowise.module.base.common.utils.ext.LogUtils.logD
import com.autowise.module.base.common.utils.ext.LogUtils.logI
import com.autowise.module.base.track.EventID
import com.autowise.module.base.track.EventType
import com.autowise.module.base.track.TrackDataBaseUtils
import com.autowise.module.base.track.TrackEntity
import com.autowise.module.base.view.ConfirmDialog
import com.autowise.module.base.view.StatusLayout

/**
 * User: chenyun
 * Date: 2021/10/13
 * Description:
 * FIXME
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected val TAG = "Lifecycle_"+this.javaClass.simpleName

    protected lateinit var v: T
    private var localHidden = false

    private val lifecycleOwner: LifecycleOwner by lazy { this }

    //监听返回键
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
//            dismissLoading() TODO:关闭页面时取消loading，目前是在vm.onclear中关闭loading
            if (onBackPressed()) {
                return
            }
            requireActivity().finish()
        }
    }

    /**
     * navigation 监听返回键
     * @return 是否处理过， true 处理了，不下发  ； false没处理，下发
     */
    open fun onBackPressed(): Boolean {
        return try {
            findNavController().navigateUp()
        } catch (e: Exception) {
            false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (registerBackPressListener()) {
            requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        }
    }

    open fun registerBackPressListener(): Boolean {
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logI(TAG, "  onCreate ")
        TrackDataBaseUtils.addNewData(
            TrackEntity(
                EventType.PAGE,
                EventID.PAGE_IN,
                this.javaClass.simpleName,
                ""
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logI(TAG, "  onViewCreated ")

    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        logI(TAG, "  onCreateView "+this::v.isInitialized)
        //防止navigation重复创建view
        if (!this::v.isInitialized) {
            context?.setDensity()
            getIntentData()
            // navigation切换会执行onDestroyView,不会执行fragment的onDestroy。 这里v会持有rootview。导致 执行onDestroyView后，view不会被销毁。
            v = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)//TODO：这个方法修改了App中的Local
            LanguageUtil.updateConfiguration(AppContext.app,"app") //在重新创建任务栈的时候，由于上面的方法会导致之前语言的设置失效，重新设置一次
            LanguageUtil.updateConfiguration(requireContext(),"activity") //在重新创建任务栈的时候，由于上面的方法会导致之前语言的设置失效，重新设置一次
            initView()
            initViewModel()?.apply {
                setViewModel(this)//TODO: 不够好
            }
            initListener()
            refreshData()
        }
        return v.root
    }

    open fun initListener() {

    }

    open fun initViewModel(): BaseVM? {
        return null
    }

    /**
     * 设置viewModel，实现显示loadding效果
     */
    fun setViewModel(vm: BaseVM?) {
        //TODO：可能收不到showloading
        vm?.isShowLoading?.observe(lifecycleOwner) {
            if (it.isShowLoading) {
                showLoading(it.cancelable)
            } else {
                dismissLoading()
            }
        }
        vm?.isShowError?.observe(lifecycleOwner) { //TODO: 不能使用viewLifecycleOwner，会触发ON_DESTROY,导致被销毁LifecycleBoundObserver.onStateChanged
            if (it == true) {
                showError()
            } else {
                showNormal()
            }
        }
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()
    open fun getIntentData() {}

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        logI(TAG, " onHiddenChanged: $hidden")
        if (isAdded) {
            childFragmentManager.fragments.forEach {
                logI(TAG, it.javaClass.simpleName)
                it.onHiddenChanged(hidden)
            }
        } else {
            logI(TAG, "onHiddenChanged: isAdd " + isAdded)
        }
        localHidden = hidden
        loadData(!hidden)
    }

    /**
     *   @param visible  页面是否可见
     *   true   加载数据
     *   false  关闭数据加载
     */
    open fun loadData(visible: Boolean) {
        logI(TAG, "loadData: $visible")
    }

    override fun onResume() {
        super.onResume()
        logI(TAG, "  onResume   ${hashCode()} hide = " + localHidden)
        if (!localHidden) {
            loadData(true)
        }
    }

    override fun onPause() {
        super.onPause()
        logI(TAG, "  onPause   ${hashCode()}  hide = " + localHidden)
        if (!localHidden) {
            loadData(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.logD(TAG, "  onDestroy   ${hashCode()}")
        TrackDataBaseUtils.addNewData(
            TrackEntity(
                EventType.PAGE,
                EventID.PAGE_OUT,
                this.javaClass.simpleName,
                ""
            )
        )
    }

    fun showConfirmDialog(
        title: String? = null,
        content: String? = null,
        sureTxt: String? = null,
        cancelTxt: String? = null,
        sureListener: () -> Unit?,
        cancelListener: () -> Unit? = {},
        cancelVisible: Boolean? = true,
        cancelable: Boolean = false,
    ) {
        val dialog = ConfirmDialog()
        dialog.show(childFragmentManager, "ConfirmDialog")
        dialog.setData(title, content,sureTxt,cancelTxt, sureListener, cancelListener,cancelVisible,{},cancelable)
    }

    /******************显示Loading页面******************************/

    fun showLoading(cancelable: Boolean = true) {
        logD(TAG, "BaseFragment---> showLoading")
        if (requireActivity() is BaseActivity<*>) {
            (requireActivity() as BaseActivity<*>).showLoading(cancelable)
        }
    }

    fun dismissLoading() {
        logD(TAG,"BaseFragment---> dismissLoading")
        if (requireActivity() is BaseActivity<*>) {
            (requireActivity() as BaseActivity<*>).dismissLoading()
        }
    }


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
        statusLayout = StatusLayout(requireContext()).apply {
            addToContent(
                viewGroup,
                this@BaseFragment::refreshData
            )
        }
    }

    /******************显示错误页面******************************/

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

    override fun onStop() {
        super.onStop()
        logI(TAG, "  onStop " )
    }

    override fun onDestroyView() {
        logI(TAG, "  onDestroyView ")
        super.onDestroyView()
//        dismissLoading()
    }

    override fun onDetach() {
        super.onDetach()
        logI(TAG, "  onDetach ")
    }

}