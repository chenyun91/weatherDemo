package com.autowise.module.base.camera

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.activity.result.contract.ActivityResultContract
import com.autowise.module.base.common.utils.ext.LogUtils.logI
import java.io.File

/**
 * User: chenyun
 * Date: 2022/7/12
 * Description: 选择照片
 * https://blog.csdn.net/java_android_man/article/details/120809631
 * FIXME
 */
class SelectPhotoContract : ActivityResultContract<Unit, Uri?>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(Intent.ACTION_PICK).setType("image/*")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return intent?.data
    }
}