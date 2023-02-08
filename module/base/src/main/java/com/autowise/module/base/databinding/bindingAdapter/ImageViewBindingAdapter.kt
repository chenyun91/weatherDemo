package com.autowise.module.base.databinding.bindingAdapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.autowise.module.base.AppContext
import com.autowise.module.view.ext.ViewExt.getColorInt
import com.autowise.module.view.ext.ViewExt.setSVGImageColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * User: chenyun
 * Date: 2022/5/12
 * Description: http://events.jianshu.io/p/5afebba80e65
 * FIXME
 */
object ImageViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["imagePath", "placeHolder"], requireAll = false)
    fun setGlideImage(iv: ImageView, imagePath: String?, placeHolder: Drawable?) {
        Glide.with(iv.context)
            .load(imagePath)
            .placeholder(placeHolder) //                .asBitmap()//只加载静态图片，如果是git图片则只加载第一帧。
            //                .error(placeholder) //加载错误显示
            .diskCacheStrategy(DiskCacheStrategy.ALL) //                .centerInside()
            .into(iv)
    }


    @JvmStatic
    @BindingAdapter(value = ["color", "svgImage"], requireAll = true)
    fun setSvgImageColor(view: ImageView, color: Int, svgImage: Int) {
        view.setSVGImageColor(AppContext.app.getColorInt(color), svgImage)
    }

}