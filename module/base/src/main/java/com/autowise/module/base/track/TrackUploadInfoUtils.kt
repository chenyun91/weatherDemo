package com.autowise.module.base.track

import android.os.Handler
import com.autowise.module.base.AppContext
import com.autowise.module.base.common.configs.AppCoreInit
import com.autowise.module.base.common.configs.ENV
import com.autowise.module.base.common.configs.LogoutListener
import com.autowise.module.base.common.utils.ext.LogUtils
import com.autowise.module.base.common.utils.ext.LogUtils.logI
import com.autowise.module.base.exception.CrashLogException
import com.autowise.module.base.network.BaseObserver
import com.autowise.module.base.network.BaseResponse
import com.autowise.module.base.track.TrackDataBaseUtils.getTotalCount
import com.autowise.module.base.track.TrackDataBaseUtils.loadData
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.StringBuilder


/**
 * User: chenyun
 * Date: 2022/9/22
 * Description: 上传数据
 *
 *  修改上传逻辑：
 *  插入数据时，检查数据大于20条,上传
 *  定时器10s钟轮询上传
 */
object TrackUploadInfoUtils {
    val TAG = "TrackUploadInfoUtils"
    const val TOTAL_COUNT =
        20 //数据大于20条执行上传，对应 TrackDao.loadAll()方法中的：  @Query("select * from trackInfo limit 20")
    const val LOOP_TIME = 10000L //轮询时间
    var reconnectCount = 1

    val createStr = "{\"create\":{}}\n"
    val elkPath =
        if (AppContext.env == ENV.TEST) "/test_index_xiebo_01/_bulk" else "/wiaction_app_index_ds/_bulk"

    val handler: Handler by lazy { Handler() }
//    val runnable = Runnable {
//        LogUtils.logI(TAG, "收到消息 runnable")
//        if (isInit) {
//            postMessage()
//            GlobalScope.launch(Dispatchers.IO) {
//                //获取本地数据库中，数据量 ，当数据量大于TOTAL_COUNT时，进行上传。
//                val totalCount = getTotalCount()
//                LogUtils.logI(TAG, "数据库中消息总数量 " + totalCount)
//                if (totalCount >= TOTAL_COUNT) {
//                    //先获取到数据库中的数据，然后进行上传
//                    try {
//                        postTrackInfo(loadData())
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }
////                }
//            }
//        }
//    }
//
//
//    /**
//     * 发送消息，开始循环
//     */
//    private fun postMessage() {
//        handler.postDelayed(runnable, (reconnectCount++) * LOOP_TIME)
//    }

    val runnable = Runnable {
        LogUtils.logI(TAG, "收到消息 runnable")
        if (isInit) {
            postMessage()
            GlobalScope.launch(Dispatchers.IO) {
                //获取本地数据库中，数据量 ，当数据量大于TOTAL_COUNT时，进行上传。
                //先获取到数据库中的数据，然后进行上传
                try {
                    postTrackInfo(loadData())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    /**
     * 发送消息，开始循环
     */
    private fun postMessage() {
        handler.postDelayed(runnable, LOOP_TIME)
    }


    var isInit = false

    init {
        AppCoreInit.list.add(object : LogoutListener {
            override fun logout() {
                onClear()
            }
        })
    }

    /**
     * 在登陆后，开启数据上传服务
     * 在退出登录后，关闭数据上传操作
     */
    fun initUploadDataLooper() {
        insertErrorLog()
        if (!AppContext.uploadTrackInfo) {
            return
        }
        if (isInit) {
            return
        }
        reconnectCount = 1
        isInit = true
        postMessage()
    }

    /**
     * 上传数据
     */
    private fun postTrackInfo(list: List<TrackEntity>) {
        if (AppContext.token.isNullOrEmpty()) {
            onClear()
            return
        } //没有token时，不用上传数据
        if (list.isEmpty()) return
        LogUtils.logI(TAG, "准备上传数据 " + Thread.currentThread().name)
        postTrackInfoToElk(list)
//        postTrackInfoToServer(list)

    }

    private fun postTrackInfoToServer(list: List<TrackEntity>) {
        TrackRepository.postTrackInfo(list).subscribe(object : BaseObserver<TrackResponse>() {
            override fun onSuccess(t: BaseResponse<TrackResponse>) {
                super.onSuccess(t)
                reconnectCount = 1
                TrackDataBaseUtils.deleteData(list) //上传成功后，删除数据
            }

            override fun onFailOrError(msg: String?) {
            }

            override fun onComplete() {
            }
        })
    }

    /**
     * 上传数据到elk日志平台
     * http://172.21.2.25:5601/app/home#/
     */
    private fun postTrackInfoToElk(list: List<TrackEntity>) {
        val sb = StringBuilder()
        list.forEach {
            sb.append(createStr)
            sb.append(
                JSONObject().apply {
                    put("@timestamp", it.timestamp)
                    put("event.dataset", "WiAction")
                    put("message", it.toString())
                }.toString()
            )
            sb.append("\n")
        }
        sb.append("\n") //必须以\n结束
        val mediaType = "application/json".toMediaTypeOrNull()
        val requestBody = sb.toString().toRequestBody(mediaType)
        TrackELKRepository.postTrackInfoToElk(elkPath, requestBody)
            .subscribe(object : Observer<Unit> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Unit) {
                    TrackDataBaseUtils.deleteData(list) //上传成功后，删除数据
                    LogUtils.logI(TAG, "上传成功 ")
                }

                override fun onError(e: Throwable) {
                    LogUtils.logI(TAG, "上传失败 ")
                }

                override fun onComplete() {
                }
            })
    }

    fun onClear() {
        isInit = false
    }


    /**
     * 插入数据时，检查数据条数
     * 大于20条，进行上传
     */
    fun CoroutineScope.insertDataCheckUpload() {
        if (!isInit) return
        val totalCount = getTotalCount()
        LogUtils.logI(TAG, "数据库中消息总数量 " + totalCount)
        if (totalCount >= TOTAL_COUNT) {
            try {
                postTrackInfo(loadData())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertErrorLog() {
        val errorLog = CrashLogException.getInstance().errorLog ?: return
        //上传错误日志
        GlobalScope.launch(Dispatchers.IO) {
            TrackDataBaseUtils.addNewData(
                TrackEntity(
                    EventType.ERROR,
                    EventID.CRASH,
                    PageID.APP_CRASH,
                    errorLog
                )
            )
        }
    }
}