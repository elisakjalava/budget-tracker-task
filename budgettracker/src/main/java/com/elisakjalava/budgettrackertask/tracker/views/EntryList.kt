package com.elisakjalava.budgettrackertask.tracker.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elisakjalava.budgettrackertask.data.entities.Entry
import kotlinx.coroutines.flow.StateFlow
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Composable
fun EditButton(entry: Entry, onClickListener: EditEntryClickListener) {
    IconButton(onClick = { onClickListener.onEditEntryClicked(entry) }) {
        Icon(Icons.Filled.Edit, "Edit entry", tint = Color.Gray)
    }
}

@Composable
fun AddNewEntryButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(Icons.Filled.AddCircle, "Add a new entry", tint = Color.Green)
    }
}

@Composable
fun NewEntryRow(onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Add an entry",
            fontWeight = FontWeight.Bold
        )
        AddNewEntryButton(onClick)
    }
}

@Composable
fun EntryRow(
    entry: Entry,
    entryClickListener: EditEntryClickListener
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = entry.amount.toString() + " €",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = entry.description,
                fontWeight = FontWeight.Thin,
                fontStyle = FontStyle.Italic,
                maxLines = 3
            )
        }

        val format = DateTimeFormat.forPattern("dd.MM.yy HH:mm")
        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = format.print(entry.timestamp),
                fontSize = 9.sp,
                color = Color.LightGray
            )
            EditButton(entry, entryClickListener)
        }
    }
}

@Composable
fun EntryList(
    entries: StateFlow<List<Entry>>,
    entryClickListener: EditEntryClickListener,
    newEntryClickListener: () -> Unit
) {
    val entriesState = entries.collectAsState(initial = listOf())
    NewEntryRow(newEntryClickListener)
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        items(entriesState.value) { entry ->
            EntryRow(entry = entry, entryClickListener = entryClickListener)
        }
    }
}

@Preview
@Composable
fun EntryRowPreview() {
    val entry = Entry(
        0,
        amount = 25.4f,
        description = "This is a preview description..",
        month = 1,
        timestamp = DateTime.now()
    )
    val entryClickListener = object : EditEntryClickListener {
        override fun onEditEntryClicked(entry: Entry) {
            // do nothing, preview only
        }
    }
    EntryRow(entry = entry, entryClickListener = entryClickListener)
}

interface EditEntryClickListener {
    fun onEditEntryClicked(entry: Entry)
}