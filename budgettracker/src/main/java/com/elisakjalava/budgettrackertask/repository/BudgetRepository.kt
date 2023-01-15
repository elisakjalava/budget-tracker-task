package com.elisakjalava.budgettrackertask.repository

import com.elisakjalava.budgettrackertask.data.dao.BudgetDao
import com.elisakjalava.budgettrackertask.data.dao.EntryDao
import com.elisakjalava.budgettrackertask.data.entities.Budget
import com.elisakjalava.budgettrackertask.data.entities.Entry
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao,
    private val entryDao: EntryDao) {

    private val currentMonth = DateTime.now().monthOfYear

    fun addBudget(amount: Float) {
        budgetDao.insert(Budget(0, amount, currentMonth))
    }

    fun addOrUpdateEntry(entry: Entry) {
        entryDao.insertEntry(entry)
    }

    fun updateBudgetForMonth(newAmount: Float) {
        if (budgetDao.getBudget(currentMonth) == null) {
            budgetDao.insert(Budget(0, newAmount, currentMonth))
        } else {
            budgetDao.updateBudgetByMonth(newAmount, currentMonth)
        }
    }

    fun removeEntry(id: Long) {
        entryDao.deleteEntryById(id)
    }

    fun observeCurrentMonthBudget(): Flow<Float> {
        return budgetDao.observeRemainingBudgetForMonth(currentMonth)
    }

    fun observeCurrentMonthEntries(): Flow<List<Entry>> {
        return entryDao.observeEntriesForMonth(currentMonth)
    }

}