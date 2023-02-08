package com.autowise.module.base

import android.os.Process
import com.autowise.module.base.common.configs.ENV
import com.autowise.module.base.common.utils.storage.BaseSp
import com.autowise.module.base.databinding.DlgDebugBinding

/**
 * User: chenyun
 * Date: 2022/11/29
 * Description:
 * FIXME
 */
class DebugDialog : BaseDialogFragment<DlgDebugBinding>() {

    override fun getLayoutId() = R.layout.dlg_debug

    var env = AppContext.env
    var isDebug = AppContext.isDebug
    var uploadTrackInfo = AppContext.uploadTrackInfo

    override fun initView() {
        notifyView()
        v.tvProdEu.setOnClickListener {
            env = ENV.PROD_EU
            notifyView()
        }
        v.tvProdZh.setOnClickListener {
            env = ENV.PROD_CN
            notifyView()
        }
        v.tvTest.setOnClickListener {
            env = ENV.TEST
            notifyView()
        }
        v.tvDebug.setOnClickListener {
            isDebug = !isDebug
            notifyView()
        }
        v.tvUpload.setOnClickListener {
            uploadTrackInfo = !uploadTrackInfo
            notifyView()
        }
        v.tvExit.setOnClickListener {
            BaseSp().putString(BaseSp.ENV, env) //设置环境
            BaseSp().putBoolean(BaseSp.DEBUG, isDebug) //开启查看debug信息
            BaseSp().putBoolean(BaseSp.UPLOAD_TRACK_INFO, uploadTrackInfo) //开启查看debug信息
            Process.killProcess(Process.myPid())
        }
    }

    private fun notifyView() {
        v.tvProdEu.isSelected = env == ENV.PROD_EU
        v.tvProdZh.isSelected = env == ENV.PROD_CN
        v.tvTest.isSelected = env == ENV.TEST
        if (isDebug) {
            v.tvDebug.text = "关闭debug"
        } else {
            v.tvDebug.text = "开启debug"
        }
        if (uploadTrackInfo) {
            v.tvUpload.text = "关闭日志上传"
        } else {
            v.tvUpload.text = "开启日志上传"
        }
    }

}