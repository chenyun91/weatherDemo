package com.autowise.module.base

import android.os.Bundle
import androidx.core.os.bundleOf
import com.autowise.module.view.ext.ViewExt.toVisible

/**
 * User: chenyun
 * Date: 2021/10/27
 * Description:
 * FIXME
 */
class WebFragment : BaseWebFragment() {

    override fun initView() {
        super.initView()
        val title = arguments?.getString("title") ?: ""
        v.titleLayout.setTitle(title)
        v.titleLayout.toVisible(title.isNotEmpty())
        v.titleLayout.onBackClickListener = {
            onBackPressed()
        }
    }

    override fun loadUrl() {
        val jumpUrl = arguments?.getString("url") ?: ""
        v.webView.loadUrl(jumpUrl)
    }


    companion object {
        fun createBundle(url: String, title: String? = null): Bundle {
            return bundleOf("url" to url, "title" to title)
        }

        fun createFragment(
            url: String,
            title: String? = null
        ): WebFragment {
            return WebFragment().apply {
                arguments = bundleOf(
                    "url" to url,
                    "title" to title,
                )
            }
        }
    }

}