package com.autowise.module.constant

/**
 * User: chenyun
 * Date: 2022/4/28
 * Description:
 * FIXME
 */
object RoutePath {
    //登陆模块
    const val LOGIN_ACTIVITY = "/login/loginActivity"
    const val LOGIN_VERIFICATION_ACTIVITY = "/login/VerificationActivity"
    const val LOGIN_EMAIL_VERIFICATION_ACTIVITY = "/login/EmailVerificationActivity"
    const val LOGIN_DEVICE_VERIFICATION_ACTIVITY = "/login/DeviceVerificationActivity"
    const val LOGIN_RESET_PASSWORD_ACTIVITY = "/login/ResetPasswordActivity"
    const val LOGIN_RESET_PASSWORD_SET_ACTIVITY = "/login/ResetPasswordSetActivity"
    const val LOGIN_CODE_VERIFY_ACTIVITY = "/login/CodeVerifyActivity"

    //车辆模块
    const val VEHICLE_SELECTION_ACTIVITY = "/vehicle/VehicleSelectionActivity"
    const val VEHICLE_START_FRAGMENT = "/vehicle/VehicleStartFragment" //启动页
    const val VEHICLE_MAP_ACTIVITY = "/vehicle/VehicleMapActivity" //高精地图页
    const val VEHICLE_SWEEP_REPORT_ACTIVITY = "/vehicle/VehicleSweepReportActivity" //清扫报告页
    const val VEHICLE_ROUT_TASK_ACTIVITY = "/vehicle/RoutTaskActivity" //任务选择页
    const val VEHICLE_VIDEO_MONITOR_ACTIVITY = "/aliyun/VideoMonitorActivity"//视频监控页


    //消息模块
    const val MESSAGE_ACTIVITY = "/message/MessageActivity"

    //个人中心
    const val MINE_ACTIVITY = "/mine/MineActivity"
    const val PROTOCOL_ACTIVITY = "/mine/ProtocolActivity"

    const val VEHICLE_MOVE_ACTIVITY = "/feature/VehicleMoveActivity"
    const val VEHICLE_MOVE_PREPARE_ACTIVITY = "/feature/VehicleMovePrepareActivity"

    const val DEBUG_ACTIVITY = "/app/DebugActivity"

}