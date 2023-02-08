package com.autowise.module.base.track

import androidx.room.*

/**
 * User: chenyun
 * Date: 2022/9/21
 * Description:
 * FIXME
 */
@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)//有了就替换
    fun insertAll(vararg entity: TrackEntity)

    /**
     * 删除user对象
     */
    @Delete
    fun delete(vararg entity: TrackEntity)

    /**
     * 查找20条数据
     */
    @Query("select * from trackInfo limit 20")
    fun loadAll(): List<TrackEntity>

    /**
     * 查找数据总条目
     */
    @Query("select count(autoId) from trackInfo")
    fun totalCount(): Int


    companion object {
        const val TABLE_TRACK = "trackInfo"
    }
}