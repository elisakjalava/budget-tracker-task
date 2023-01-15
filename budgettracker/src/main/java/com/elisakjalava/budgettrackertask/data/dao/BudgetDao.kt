package com.elisakjalava.budgettrackertask.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.elisakjalava.budgettrackertask.data.entities.Budget
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

@Dao
interface BudgetDao {
    @Insert
    fun insert(budget: Budget)

    @Query("UPDATE budget SET amount = :amount WHERE month = :month")
    fun updateBudgetByMonth(amount: Float, month: Int)

    @Query("SELECT budget.amount - SUM(entries.amount) " +
            "FROM budget JOIN entries ON budget.month = entries.month WHERE budget.month = :month")
    fun observeRemainingBudgetForMonth(month: Int): Flow<Float>

    @Query("SELECT * FROM budget WHERE month = :month")
    fun getBudget(month: Int): Budget?

}