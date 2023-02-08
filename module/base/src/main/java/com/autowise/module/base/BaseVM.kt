package com.autowise.module.base

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autowise.module.base.common.utils.ext.LogUtils
import com.autowise.module.base.common.utils.ext.LogUtils.logI
import com.autowise.module.base.data.LoadingBean

/**
 * User: chenyun
 * Date: 2022/1/7
 * Description:
 * FIXME
 */
open class BaseVM : ViewModel() {

    val TAG="Lifecycle_"+this.javaClass.simpleName
    /********************* loading相关 *******************************/
    //TODO: 使用liveData会导致页面销毁后，无法响应状态我问题  去掉这种showLoadiing的方式
    val isShowLoading = MutableLiveData<LoadingBean>()
    private var loadingBean = LoadingBean(false)

    fun showLoading(cancelable: Boolean = true) {
        loadingBean.isShowLoading = true
        loadingBean.cancelable = cancelable
        Handler().postDelayed({
            LogUtils.logD(TAG, "BaseFragment---> showLoading")
            isShowLoading.postValue(loadingBean)
        }, 1000L)
    }

    fun showLoadingImmediately(cancelable: Boolean = true) {
        LogUtils.logD(TAG, "BaseFragment---> showLoadingImmediately")
        loadingBean.isShowLoading = true
        loadingBean.cancelable = cancelable
        isShowLoading.postValue(loadingBean)
    }

    fun dismissLoading() {
        LogUtils.logD(TAG, "BaseFragment---> dismissLoading")
        loadingBean.isShowLoading = false
        isShowLoading.postValue(loadingBean)
    }

    override fun onCleared() {
        super.onCleared()
//        dismissLoading()
        logI("onCleared= " + hashCode())
    }

    /****************** 错误页面**********************************/
    val isShowError = MutableLiveData<Boolean>()

    fun showError() {
        isShowError.postValue(true)
    }

    fun dismissError() {
        isShowError.postValue(false)
    }

    /****************** 注册livedata事件**********************************/
    val sendEventType = MutableLiveData<String>() //显示哪种类型的弹窗


}