package com.autowise.module.base.track

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * User: chenyun
 * Date: 2022/9/21
 * Description:
 * FIXME
 */
@Database(entities = [TrackEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getTrackDao(): TrackDao
}