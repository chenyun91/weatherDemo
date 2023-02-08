package com.autowise.module.base.glide.engine

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

interface ImageEngine {
    /**
     * Load thumbnail of a static image resource.
     *
     * @param context     Context
     * @param resize      Desired size of the origin image
     * @param placeholder Placeholder drawable when image is not loaded yet
     * @param imageView   ImageView widget
     * @param uri         Uri of the loaded image
     */
    fun loadThumbnail(
        context: Context,
        resize: Int,
        placeholder: Drawable?,
        imageView: ImageView,
        uri: Uri?
    )

    /**
     * Load thumbnail of a gif image resource. You don't have to load an animated gif when it's only
     * a thumbnail tile.
     *
     * @param context     Context
     * @param resize      Desired size of the origin image
     * @param placeholder Placeholder drawable when image is not loaded yet
     * @param imageView   ImageView widget
     * @param uri         Uri of the loaded image
     */
    fun loadGifThumbnail(
        context: Context,
        resize: Int,
        placeholder: Drawable?,
        imageView: ImageView,
        uri: Uri?
    )

    /**
     * Load a static image resource.
     *
     * @param context   Context
     * @param imageView ImageView widget
     * @param uri       Uri of the loaded image
     */
    fun loadImage(context: Context, placeholder: Int, imageView: ImageView, uri: Uri?)

    /**
     * Load a static image resource.
     *
     * @param context   Context
     * @param imageView ImageView widget
     * @param uri       Uri of the loaded image
     */
    fun loadImage(context: Context, placeholder: Drawable, imageView: ImageView, uri: Uri?)

    /**
     * Load a static image resource.
     *
     * @param context   Context
     * @param imageView ImageView widget
     * @param imagePath Uri of the loaded image
     */
    fun loadImage(context: Context, placeholder: Int, imageView: ImageView, imagePath: String?)

    /**
     * Load a gif image resource.
     *
     * @param context   Context
     * @param imageView ImageView widget
     * @param uri       Uri of the loaded image
     */
    fun loadGifImage(context: Context, imageView: ImageView, uri: Uri?)

    /**
     * Load a gif image resource.
     *
     * @param context   Context
     * @param imageView ImageView widget
     * @param uri       Uri of the loaded image
     */
    fun loadVideoImage(context: Context, placeholder: Int, imageView: ImageView, uri: Uri?)

    /**
     * Load a net image resource.
     *
     * @param url             Uri of the loaded image
     * @param context         Context
     * @param imageView       ImageView widget
     * @param requestListener
     */
    fun loadNetImage(
        context: Context,
        placeholder: Int,
        imageView: ImageView,
        url: Uri?,
        thumbnailUrl: Uri?,
        requestListener: ImageRequestListener
    )

    /**
     * Load a net image resource.
     *
     * @param url             Uri of the loaded image
     * @param fragment        Fragment
     * @param imageView       ImageView widget
     * @param requestListener
     */
    fun loadNetImage(
        fragment: Fragment,
        placeholder: Int,
        imageView: ImageView,
        url: Uri?,
        thumbnailUrl: Uri?,
        requestListener: ImageRequestListener
    )

    /**
     * Load thumbnail of a static image resource.
     *
     * @param context        Context
     * @param resourceId     placeholderId
     * @param roundingRadius roundingRadius
     * @param imageView      ImageView widget
     * @param uri            Uri of the loaded image
     */
    fun loadThumbnailCorner(
        context: Context,
        @DrawableRes resourceId: Int,
        roundingRadius: Int,
        imageView: ImageView,
        uri: Uri?
    )

    /**
     * Whether this implementation supports animated gif.
     * Just knowledge of it, convenient for users.
     *
     * @return true support animated gif, false do not support animated gif.
     */
    fun supportAnimatedGif(): Boolean

    fun downloadImage(
        context: Context,
        uri: Uri,
        savedFilePath: String,
        imageDownloadListener: ImageDownloadListener
    )
}