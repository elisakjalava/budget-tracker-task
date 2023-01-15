package com.elisakjalava.budgettrackertask.tracker.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.elisakjalava.budgettrackertask.data.entities.Budget
import com.elisakjalava.budgettrackertask.data.entities.Entry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.joda.time.DateTime
import java.lang.Float.parseFloat

@Composable
fun RemainingBudget(remainingBudget: StateFlow<Float?>) {
    val budget = remainingBudget.collectAsState(0.0f)
    val initialText = if (budget.value == null) "0 €" else budget.value.toString()
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = initialText,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = if (budget.value != null && budget.value!! >= 0) Color.Green else Color.Red,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(
                text = " €",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row {
            Text(text = "Budget remaining this month", fontSize = 10.sp)
        }
    }

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
        modifier = Modifier
            .padding(vertical = 32.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RemainingBudget(remainingBudget)
        EditBudgetButton(editBudgetClickListener)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBudgetForMonthDialog(
    amount: Float,
    setShowDialog: (Boolean) -> Unit,
    setBudget: (Float) -> Unit
) {
    val textFieldAmount = remember { mutableStateOf(amount.toString()) }
    val focusManager = LocalFocusManager.current

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(shape = RoundedCornerShape(8.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Edit this month's budget",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "close dialog",
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = textFieldAmount.value,
                        onValueChange = { textFieldAmount.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Enter value..") },
                        label = { Text(text = "Budget (€)") },
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {
                        setBudget(parseFloat(textFieldAmount.value))
                        setShowDialog(false)
                    }) {
                        Text(text = "Done")
                    }
                }
            }
        }
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

