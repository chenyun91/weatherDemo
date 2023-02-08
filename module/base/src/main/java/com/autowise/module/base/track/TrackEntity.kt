package com.autowise.module.base.track

import android.os.Build.VERSION.SDK_INT
import android.provider.Settings
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.autowise.module.base.AppContext
import com.autowise.module.base.common.utils.AppUtils
import com.autowise.module.base.common.utils.DeviceUtils
import com.autowise.module.base.track.TrackDao.Companion.TABLE_TRACK

/**
 * User: chenyun
 * Date: 2022/9/20
 * Description:
 * TODO: 修改数据库的表项，需要执行数据库升级操作
 * https://www.jianshu.com/p/41272f319ae7?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation
 */
@Entity(tableName = TABLE_TRACK)
data class TrackEntity(
    val event_type: String,
    val event_id: String,
    val page_id: String,
    val exts: String,
    val brand: String = android.os.Build.BRAND,//Xiaomi
    val platform: String = "Android",//平台
    val device_id: String = AppContext.deviceId,//设备ID
    val device_model: String = android.os.Build.MODEL,//设备类型 MIX 3
    val device_name: String? =DeviceUtils.getDeviceName(),//设备名称
    val os_version: String = SDK_INT.toString(),//系统版本
    val app_version: String = AppUtils.getVersionName(), //App版本号
    val app_build: String = AppUtils.getVersionCode().toString(), //App数字版本号/编译号
    val timestamp: Long = System.currentTimeMillis(), //时间戳(打点时间)
    val uid: String? = AppContext.userInfo?.id //登录账号UID
) {
    //自增长主键
    @PrimaryKey(autoGenerate = true)
    var autoId: Int = 0


    override fun toString(): String {
        return "Android [$page_id:$event_id] eventType=$event_type, brand=$brand, platform=$platform, device_id=$device_id, device_model=$device_model, device_name=$device_name, os_version=$os_version, app_version=$app_version, app_build=$app_build, uid=$uid, exts=$exts"
    }
}


object EventType {
    val PAGE = "0"//page
    val CLICK = "1" //click
    val SHOW = "2" //show
    val CUSTOM = "3" //custom
    val ERROR = "4"
}

object EventID {
    val PAGE_IN = "10000" //页面切入
    val PAGE_OUT = "10001" //页面切出
    val REQUEST = "10100" //网络请求发出
    val RESPONSE = "10101" //网络请求接收
    val WSS_CONNECT = "10110" //WSS连接发起
    val WSS_STATUS_CHANGE = "10111" //WSS状态变更
    val WSS_RESPONSE = "10112" //WSS正常结果
    val IM_MESSAGE = "10200" //IM收到消息
    val START_VEHICLE = "20000" //车辆启动
    val CRASH = "10010"//App crash日志
}

object PageID {
    //  PAGE//切换的页面
    val HTTP = "http"
    val WSS = "wss"
    val IM = "im"
    val START_VEHICLE = "StartVehicle" //车辆启动/关闭 动作上报
    val APP_CRASH = "AppCrash"
}

data class TrackResponse(
    val total_count: Int,
    val success_count: Int,
    val fail_count: Int,
    val fail_msg: Any
)
