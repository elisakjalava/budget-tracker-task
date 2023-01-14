package com.elisakjalava.budgettrackertask.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elisakjalava.budgettrackertask.data.entities.Entry
import com.elisakjalava.budgettrackertask.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class BudgetTrackerViewModel @Inject constructor(
    val budgetRepository: BudgetRepository
) : ViewModel() {

    private val currentMonth = DateTime.now().monthOfYear
    private val _entries = budgetRepository.observeCurrentMonthEntries()
    val entries: StateFlow<List<Entry>> =
        _entries.stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    private val _remainingBudget = budgetRepository.observeCurrentMonthBudget()
    val remainingBudget = _remainingBudget.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0f)

    fun onEntry(entry: Entry) = viewModelScope.launch {
        budgetRepository.addOrUpdateEntry(entry)
    }

}