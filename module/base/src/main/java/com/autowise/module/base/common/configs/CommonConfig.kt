package com.autowise.module.base.common.configs

/**
 * User: chenyun
 * Date: 2021/10/25
 * Description:
 * FIXME
 */
/**
 * 环境配置
 */
object ENV {
    const val TEST = "test"
    const val PROD_CN = "prod_cn"
    const val PROD_EU = "prod_eu"
}

/**
 * 商城配置
 */
object FLAVOR {
    const val GOOGLE_PLAY = "GooglePlay"
    const val DEFAULT = "Default"
}

object URL {
    const val prodBaseUrl_EU = "https://restapi.amap.com/"
    const val prodWsBaseUrl_EU = ""
    const val prodElkUrl_EU = "X"

    const val prodBaseUrl_CN = "https://restapi.amap.com/"
    const val prodWsBaseUrl_CN = ""
    const val prodElkUrl_CN = ""

    //    const val testBaseUrl = "http://192.168.2.231:8800/"
    const val testBaseUrl = "https://restapi.amap.com/"
    const val testWsBaseUrl = ""
    const val testElkUrl = ""
}

object URI{
    //页面URI
    const val VehicleMap = "wiaction://vehicle/vehicleMap" //高精地图页
    const val SweepReport = "wiaction://report/cleanReport" //清扫报告页
    const val StartVehicle = "wiaction://vehicle/startVehicle" //车辆启动页
    const val ChooseTask = "wiaction://vehicle/chooseTask" //选择任务页
}

object ErrorCode {
    const val ParamsError = 10000 //传参错误
    const val TokenExpired = 10001 // token失效
    const val TokenVerified = 10002 //（发送、验证邮箱验证码接口）token已经被验证，可直接登录
    const val NO_VEHICLE_CONTROL_RULES = 10003 //无车辆控制权限
    const val REQUEST_VEHICLE_SERVICE_FAILED = 10004 //请求车辆服务失败
    const val SHOW_ERROR_DIALOG = 10006 //显示错误弹窗
    const val FORCE_UPGRADE = 10009 //	客户端版本低，需要强制升级
}

object RequestStatus {
    const val showLoading = "showLoading"
    const val dismissLoading = "dismissLoading"
    const val showError = "showError"
    const val dismissError = "dismissError"

}

object SCHEMA {
    const val WIACTION = "wiaction"
}

object IM {
    const val DEV_SDKAPPID = 1400666790
    const val PROD_SDKAPPID = 1400666786 //
}