package com.autowise.module.base.glide.engine

interface ImageDownloadListener {
    /**
     * @param fileName 从url里解析出来的文件名
     * @param fileExt 从url或文件中解析出来扩展名
     */
    fun onSuccess(fileName: String?, fileExt: String?)
    fun onFail()
}