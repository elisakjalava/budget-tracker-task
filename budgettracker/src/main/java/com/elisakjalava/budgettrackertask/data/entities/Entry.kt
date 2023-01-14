package com.elisakjalava.budgettrackertask.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "amount")
    val amount: Float = 0.0f,
    @ColumnInfo(name = "month")
    val month: Int,
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "timestamp")
    val timestamp: DateTime
)