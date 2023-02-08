package com.autowise.module.base.track

import com.autowise.module.base.common.utils.ext.LogUtils
import com.autowise.module.base.common.utils.ext.LogUtils.logI
import com.autowise.module.base.track.TrackUploadInfoUtils.insertDataCheckUpload
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * User: chenyun
 * Date: 2022/9/22
 * Description: 数据库的增删改查操作
 */
object TrackDataBaseUtils {

    val TAG = "TrackDataBaseUtils"

    /**
     * 插入一条数据到数据库
     */
    fun addNewData(trackEntity: TrackEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            LogUtils.logI(TAG, "插入  " + trackEntity.toString())
            AppDataBaseHelper.dataBase.getTrackDao().insertAll(trackEntity)
           insertDataCheckUpload()
        }
    }

    /**
     * 删除数据库中的数据
     */
    fun deleteData(mList: List<TrackEntity>) {
        GlobalScope.launch(Dispatchers.IO) {
            mList.forEach {
                LogUtils.logI(TAG, "删除  " + it.toString())
                AppDataBaseHelper.dataBase.getTrackDao().delete(it)
            }
        }
    }

    /**
     * 查询数据库中的数据
     */
    fun CoroutineScope.loadData(): List<TrackEntity> {
        val mList = AppDataBaseHelper.dataBase.getTrackDao().loadAll()
        mList.forEach {
            LogUtils.logI(TAG, "查询  " + it.toString())
        }
        return mList
    }


    fun CoroutineScope.getTotalCount(): Int {
        return AppDataBaseHelper.dataBase.getTrackDao().totalCount()
    }


}