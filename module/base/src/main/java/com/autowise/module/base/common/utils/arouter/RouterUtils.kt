package com.autowise.module.base.common.utils.arouter

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.autowise.module.constant.RoutePath

/**
 * User: chenyun
 * Date: 2022/6/22
 * Description:
 * FIXME
 */
object RouterUtils {


    /**
     * 跳转到车辆选择页
     * @param dest 目标页面
     */
    fun navigateToVehicleSelectionActivity(vehicleId: String, dest: String) {
        ARouter.getInstance().build(RoutePath.VEHICLE_SELECTION_ACTIVITY)
            .withString("dest", dest)
            .withString("vehicle_id", vehicleId)
            .navigation()
    }

    /**
     * 跳转到首页，开启新的任务栈
     */
    fun navigateToMain() {
        ARouter.getInstance().build(RoutePath.VEHICLE_SELECTION_ACTIVITY)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

}