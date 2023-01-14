package com.elisakjalava.budgettrackertask.tracker

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.elisakjalava.budgettrackertask.data.entities.Entry
import com.elisakjalava.budgettrackertask.tracker.views.EditEntryClickListener
import com.elisakjalava.budgettrackertask.tracker.views.EntryEditorDialog
import com.elisakjalava.budgettrackertask.tracker.views.RemainingBudgetView
import org.joda.time.DateTime

class MainActivity: AppCompatActivity() {

    val currentMonth = DateTime.now().monthOfYear
    val viewModel: BudgetTrackerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {

        }
    }

    @Composable
    fun MainLayout() {
        val showEditEntryDialog = remember { mutableStateOf(false) }
        val entryDialogEditorTitle = remember { mutableStateOf("Add new entry") }
        val entryState = remember { mutableStateOf(Entry(0, 0.0f, currentMonth, "", DateTime.now()))}

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

        if (showEditEntryDialog.value) {
            EntryEditorDialog(
                title = entryDialogEditorTitle.value,
                entry = entryState.value,
                setShowDialog = {
                    showEditEntryDialog.value = it
                },
                setEntry = {
                    viewModel.onEntry(it)
                }
            )
        }

        // Todo RemainingBudgetView(remainingBudget = viewModel.remainingBudget, )
    }

}