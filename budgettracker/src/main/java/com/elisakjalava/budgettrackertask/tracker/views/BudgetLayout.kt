package com.elisakjalava.budgettrackertask.tracker.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun RemainingBudget(remainingBudget: StateFlow<Float>) {
    val budget = remainingBudget.collectAsState()
    Text(
        text = budget.value.toString() + " €",
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = if (budget.value > 0) Color.Green else Color.Red,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun EditBudgetButton(editBudgetClickListener: () -> Unit) {
    Button(
        onClick = editBudgetClickListener
    ) {
        Text(
            text = "Edit budget",
            fontStyle = FontStyle.Italic
        )
        Icon(Icons.Filled.Edit, "Edit budget", tint = Color.Gray)
    }
}

@Composable
fun RemainingBudgetView(
    remainingBudget: StateFlow<Float>,
    editBudgetClickListener: () -> Unit
) {
    Row(
        modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RemainingBudget(remainingBudget)
        EditBudgetButton(editBudgetClickListener)
    }
}

@Composable
@Preview
fun PreviewRemainingBudgetView() {
    val remainingBudget = MutableStateFlow(245.20f)
    val editBudgetClickListener = {}

    RemainingBudgetView(
        remainingBudget = remainingBudget.asStateFlow(),
        editBudgetClickListener = editBudgetClickListener
    )
}

