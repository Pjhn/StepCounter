package com.pjhn.stepcounter.util

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun timeStampToLocalDate(timeStamp: Long?): LocalDate? {
        return timeStamp?.let { LocalDate.ofEpochDay(it) }
    }
    @TypeConverter
    fun localDateTimeToTimeStamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}