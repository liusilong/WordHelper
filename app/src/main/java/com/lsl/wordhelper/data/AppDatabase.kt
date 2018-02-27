package com.lsl.wordhelper.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import android.arch.persistence.db.SupportSQLiteDatabase


/**
 * Created by liusilong on 2018/1/16.
 * version:1.0
 * Describe:
 */
@Database(entities = arrayOf(Word::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private val DATABASE_NAME = "word-db"
        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, DATABASE_NAME)
                                .build()
                    }
                }
            }
            return INSTANCE as AppDatabase
        }


        //数据库版本变更
//        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE word "
//                        + " ADD COLUMN date TEXT")
//            }
//        }
    }


    abstract fun wordDao(): WordDao

}