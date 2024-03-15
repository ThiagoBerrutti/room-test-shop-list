package com.example.roomtest.database.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date


open class DateConverter {
    @TypeConverter
    fun toDate(date:Long?): Date? = date?.let { Date(it)}

    @TypeConverter
    fun fromDate(date:Date?): Long? = date?.time
}