package com.lsl.wordhelper.data

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by liusilong on 2018/1/16.
 * version:1.0
 * Describe:
 */
class DateConverter {
    companion object {
        @JvmStatic
        @TypeConverter
        fun toDate(timestamp: Long) = Date(timestamp)

        @JvmStatic
        @TypeConverter
        fun toTimestamp(date: Date): Long = date.time
    }
}