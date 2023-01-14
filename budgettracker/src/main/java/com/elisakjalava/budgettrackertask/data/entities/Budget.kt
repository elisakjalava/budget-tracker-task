package com.elisakjalava.budgettrackertask.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "amount")
    val amount: Float,
    @ColumnInfo(name = "month")
    val month: Int
)