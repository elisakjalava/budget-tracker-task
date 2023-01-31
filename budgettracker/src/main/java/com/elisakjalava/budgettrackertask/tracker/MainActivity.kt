package com.elisakjalava.budgettrackertask.tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.elisakjalava.budgettrackertask.data.entities.Entry
import com.elisakjalava.budgettrackertask.tracker.views.*
import com.elisakjalava.budgettrackertask.ui.BudgetTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetTrackerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainLayout()
                }
            }
        }
    }

}


@Composable
fun MainLayout(
) {
    val viewModel: BudgetTrackerViewModel = hiltViewModel()
    val currentMonth = DateTime.now().monthOfYear
    val showEditEntryDialog = remember { mutableStateOf(false) }
    val showEditBudgetDialog = remember { mutableStateOf(false) }
    val entryDialogEditorTitle = remember { mutableStateOf("Add new entry") }
    val entryState = remember { mutableStateOf(
        Entry(0, 0.0f, currentMonth, "", DateTime.now())
    ) }

    val editEntryClickListener = object : EditEntryClickListener {
        override fun onEditEntryClicked(entry: Entry) {
            showEditEntryDialog.value = true
            entryDialogEditorTitle.value = "Edit entry"
            entryState.value = entry
        }
    }

    val addEntryClicked = {
        entryDialogEditorTitle.value = "Add new entry"
        showEditEntryDialog.value = true
        entryState.value = Entry(0, 0.0f, currentMonth, "", DateTime.now())
    }

    val editBudgetClicked = {
        showEditBudgetDialog.value = true

    }

    if (showEditEntryDialog.value) {
        EntryEditorDialog(
            title = entryDialogEditorTitle.value,
            entry = entryState.value,
            setShowDialog = {
                showEditEntryDialog.value = it
            },
            setEntry = { viewModel.onEntry(it) }
        )
    }

    if (showEditBudgetDialog.value) {
        EditBudgetForMonthDialog(
            amount = 0.0f,
            setShowDialog = {
                showEditBudgetDialog.value = it
            }, setBudget = { viewModel.onBudget(it) }
        )
    }
    Column {
        RemainingBudgetView(
            remainingBudget = viewModel.remainingBudget,
            editBudgetClickListener = editBudgetClicked,
            totalAmountFlow = viewModel.totalEntries
        )
        EntryList(
            entries = viewModel.entries,
            entryClickListener = editEntryClickListener,
            newEntryClickListener = addEntryClicked
        )
    }
}