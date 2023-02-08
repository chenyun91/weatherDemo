package com.autowise.module.base.camera

import android.hardware.Camera

class CameraUtil {
    /**
     * 判断相机是否可用
     * @return true,相机驱动可用，false,相机驱动不可用
     */
    companion object {
        fun isCameraCanUse(): Boolean {
            var canUse = true
            var mCamera: Camera? = null
            try {
                mCamera = Camera.open()
            } catch (e: Exception) {
                canUse = false
            }
            if (canUse) {
                mCamera?.release()
                mCamera = null
            }
            return canUse
        }
    }

}