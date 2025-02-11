package com.example.stepcounterapp.util

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {
    @TypeConverter
    fun timeStampToLocalDateTime(timeStamp: Long?): LocalDateTime? {
        return timeStamp?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)}
    }
    @TypeConverter
    fun localDateTimeToTimeStamp(date: LocalDateTime?): Long? {
        return date?.toEpochSecond(ZoneOffset.UTC)
    }
}