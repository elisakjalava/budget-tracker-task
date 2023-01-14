package com.elisakjalava.budgettrackertask.data.dao

import androidx.room.*
import com.elisakjalava.budgettrackertask.data.entities.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEntry(entry: Entry)

    @Query("SELECT * FROM entries WHERE month = :month")
    fun observeEntriesForMonth(month: Int): Flow<List<Entry>>

    @Query("DELETE FROM entries WHERE id = :id")
    fun deleteEntryById(id: Long)

}