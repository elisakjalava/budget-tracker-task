package com.elisakjalava.budgettrackertask.data

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

class DateTimeConverter {

    private val formatter = ISODateTimeFormat.dateTime().withZoneUTC()

    @TypeConverter
    fun fromTimestamp(dateTime: DateTime?): String? {
        return if (dateTime == null) null else formatter.print(dateTime)
    }

    @TypeConverter
    fun toTimestamp(dateString: String?): DateTime? {
        return if (dateString == null) null else DateTime.parse(dateString, formatter)
    }

}