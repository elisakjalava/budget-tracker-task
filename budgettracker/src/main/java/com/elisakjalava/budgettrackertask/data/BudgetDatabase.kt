package com.elisakjalava.budgettrackertask.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elisakjalava.budgettrackertask.data.dao.BudgetDao
import com.elisakjalava.budgettrackertask.data.dao.EntryDao
import com.elisakjalava.budgettrackertask.data.entities.Budget
import com.elisakjalava.budgettrackertask.data.entities.Entry

@Database(entities = [Entry::class, Budget::class], version = 1)
@TypeConverters(DateTimeConverter::class)
abstract class BudgetDatabase: RoomDatabase() {

    abstract fun budgetDao(): BudgetDao
    abstract fun entryDao(): EntryDao

    companion object {
        @Volatile
        private var instance: BudgetDatabase? = null

        fun getInstance(context: Context): BudgetDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): BudgetDatabase {
            val db = Room.databaseBuilder(
                context,
                BudgetDatabase::class.java,
                "budget_database"
            )
            return db.build()
        }
    }

}