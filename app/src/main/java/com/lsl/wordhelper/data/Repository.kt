package com.lsl.wordhelper.data

import android.content.Context

/**
 * Created by liusilong on 2018/1/16.
 * version:1.0
 * Describe:
 */
object Repository {
    fun providerWordRepository(context: Context): WordDao {
        return AppDatabase.getInstance(context).wordDao()
    }
}
