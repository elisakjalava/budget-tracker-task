package com.elisakjalava.budgettrackertask.data

import android.content.Context
import com.elisakjalava.budgettrackertask.data.dao.BudgetDao
import com.elisakjalava.budgettrackertask.data.dao.EntryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BudgetDatabase
        = BudgetDatabase.getInstance(context)

    @Provides
    fun provideBudgetDao(database: BudgetDatabase): BudgetDao = database.budgetDao()

    @Provides
    fun provideEntryDao(database: BudgetDatabase): EntryDao = database.entryDao()

}