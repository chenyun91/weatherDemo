package com.autowise.module.base.glide.engine.impl

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.autowise.module.base.AppContext
import com.autowise.module.base.common.utils.UiUtils
import com.autowise.module.base.glide.engine.ImageDownloadListener
import com.autowise.module.base.glide.engine.ImageEngine
import com.autowise.module.base.glide.engine.ImageRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.File

class GlideEngine : ImageEngine {
    override fun loadThumbnail(
        context: Context,
        resize: Int,
        placeholder: Drawable?,
        imageView: ImageView,
        uri: Uri?
    ) {
        Glide.with(context).load(uri).override(resize, resize).centerCrop().placeholder(placeholder)
            .into(imageView)
    }

    override fun loadGifThumbnail(
        context: Context,
        resize: Int,
        placeholder: Drawable?,
        imageView: ImageView,
        uri: Uri?
    ) {
        Glide.with(context).load(uri).override(resize, resize).centerCrop().placeholder(placeholder)
            .into(imageView)
    }

    override fun loadImage(context: Context, placeholder: Int, imageView: ImageView, uri: Uri?) {
        Glide.with(context)
            .load(uri)
            .override(UiUtils.getScreenWidth(context), Int.MAX_VALUE)
            .priority(Priority.HIGH)
            .fitCenter()
            .placeholder(placeholder)
            .dontAnimate()
            .into(imageView)
    }

    override fun loadImage(
        context: Context,
        placeholder: Drawable,
        imageView: ImageView,
        uri: Uri?
    ) {
        Glide.with(context)
            .load(uri)
            .override(UiUtils.getScreenWidth(context), Int.MAX_VALUE)
            .priority(Priority.HIGH)
            .fitCenter()
            .placeholder(placeholder)
            .dontAnimate()
            .into(imageView)
    }

    override fun loadImage(
        context: Context,
        placeholder: Int,
        imageView: ImageView,
        imagePath: String?
    ) {
        Glide.with(context)
            .load(imagePath)
            .override(UiUtils.getScreenWidth(context), Int.MAX_VALUE)
            .priority(Priority.HIGH)
            .fitCenter()
            .placeholder(placeholder)
            .dontAnimate()
            .into(imageView)
    }

    override fun loadGifImage(context: Context, imageView: ImageView, uri: Uri?) {
        Glide.with(context)
            .load(uri)
            .override(UiUtils.getScreenWidth(context), Int.MAX_VALUE)
            .priority(Priority.HIGH)
            .fitCenter()
            .dontAnimate()
            .into(imageView)
    }

    override fun loadVideoImage(
        context: Context,
        placeholder: Int,
        imageView: ImageView,
        uri: Uri?
    ) {
        Glide.with(context)
            .load(uri)
            .override(UiUtils.getScreenWidth(context), Int.MAX_VALUE)
            .priority(Priority.HIGH)
            .fitCenter()
            .placeholder(placeholder)
            .dontAnimate()
            .into(imageView)
    }

    override fun loadNetImage(
        context: Context,
        placeholder: Int,
        imageView: ImageView,
        uri: Uri?,
        thumbnailUrl: Uri?,
        galleryRequestListener: ImageRequestListener
    ) {
        Glide.with(context)
            .load(uri)
            .thumbnail(
                Glide.with(context).load(thumbnailUrl)
                    .override(UiUtils.getScreenWidth(context), UiUtils.getScreenWidth(context))
            )
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    galleryRequestListener.onLoadFailed()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    galleryRequestListener.onResourceReady()
                    return false
                }

            })
            .priority(Priority.HIGH)
            .override(UiUtils.getScreenWidth(context), Int.MAX_VALUE)
            .fitCenter()
            .placeholder(placeholder)
            .dontAnimate()
            .into(imageView)
    }

    override fun loadNetImage(
        fragment: Fragment,
        placeholder: Int,
        imageView: ImageView,
        uri: Uri?,
        thumbnailUrl: Uri?,
        galleryRequestListener: ImageRequestListener
    ) {
        Glide.with(fragment)
            .load(uri)
            .thumbnail(
                Glide.with(fragment).load(thumbnailUrl).override(
                    UiUtils.getScreenWidth(AppContext.app),
                    UiUtils.getScreenWidth(AppContext.app)
                )
            )
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    galleryRequestListener.onLoadFailed()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    galleryRequestListener.onResourceReady()
                    return false
                }

            })
            .priority(Priority.HIGH)
            .override(UiUtils.getScreenWidth(AppContext.app), Int.MAX_VALUE)
            .fitCenter()
            .placeholder(placeholder)
            .dontAnimate()
            .into(imageView)
    }

    override fun loadThumbnailCorner(
        context: Context,
        resourceId: Int,
        roundingRadius: Int,
        imageView: ImageView,
        uri: Uri?
    ) {
        Glide.with(context)
            .asBitmap()
            .load(uri)
            .transform(CenterCrop(), GlideRoundTransform())
            .placeholder(resourceId)
            .dontAnimate()
            .into(imageView)
    }

    override fun supportAnimatedGif(): Boolean {
        return true
    }

    override fun downloadImage(
        context: Context,
        uri: Uri,
        savedFilePath: String,
        imageDownloadListener: ImageDownloadListener
    ) {
       /* Glide.with(context).downloadOnly().load(uri).listener(object : RequestListener<File> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<File>?,
                isFirstResource: Boolean
            ): Boolean {
                imageDownloadListener.onFail()
                return false
            }

            override fun onResourceReady(
                resource: File?,
                model: Any?,
                target: Target<File>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                if (resource != null) {
                    try {
                        FileUtils.copyFile(resource, File(savedFilePath))
                        var fileExt =
                            android.webkit.MimeTypeMap.getFileExtensionFromUrl(uri.toString())
                        if (fileExt.isNullOrBlank()) {
                            fileExt = ImageUtils.getFileExtension(resource.path)
                        }
                        if (fileExt.isNullOrBlank()) {
                            fileExt = null
                        }
                        val fileName: String =
                            uri.toString().substring(uri.toString().lastIndexOf('/') + 1)

                        imageDownloadListener.onSuccess(fileName, fileExt)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    imageDownloadListener.onFail()
                }
                return false
            }
        }).submit()*/
    }
}