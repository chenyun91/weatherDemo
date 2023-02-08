package com.autowise.module.base.track

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.autowise.module.base.AppContext

object AppDataBaseHelper {
    private val databseName: String = "trackInfoDatabase"

    val dataBase: AppDataBase by lazy {
        Room.databaseBuilder(
            AppContext.app,
            AppDataBase::class.java,
            databseName
        )
//            .addMigrations(MIGRATION_1_2) //添加升级规则
//            .allowMainThreadQueries()
            .build()
    }

    //升级规则
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE trackInfo ADD COLUMN event_name TEXT"
            )
        }
    }

}