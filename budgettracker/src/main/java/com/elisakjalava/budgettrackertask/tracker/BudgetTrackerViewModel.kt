package com.elisakjalava.budgettrackertask.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elisakjalava.budgettrackertask.data.entities.Entry
import com.elisakjalava.budgettrackertask.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class BudgetTrackerViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
) : ViewModel() {

    private val _entries = budgetRepository.observeCurrentMonthEntries()
    val entries: StateFlow<List<Entry>> =
        _entries.stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    private val _remainingBudget = budgetRepository.observeCurrentMonthBudget()
    val remainingBudget = _remainingBudget.stateIn(
        viewModelScope, SharingStarted.Eagerly, 0.0f
    )

    private val _totalEntries = budgetRepository.observeCurrentMonthEntryTotal()
    val totalEntries = _totalEntries.stateIn(
        viewModelScope, SharingStarted.Eagerly, 0.0f
    )

    fun onEntry(entry: Entry) = CoroutineScope(Dispatchers.IO).launch {
        budgetRepository.addOrUpdateEntry(entry)
    }

    fun onBudget(amount: Float) = CoroutineScope(Dispatchers.IO).launch {
        budgetRepository.updateBudgetForMonth(amount)
    }

}