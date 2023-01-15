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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.elisakjalava.budgettrackertask.data.entities.Entry
import org.joda.time.DateTime
import java.lang.Float.parseFloat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryEditorDialog(
    title: String,
    entry: Entry,
    setShowDialog: (Boolean) -> Unit,
    setEntry: (Entry) -> Unit
) {
    val textFieldAmount = remember { mutableStateOf(entry.amount.toString()) }
    val textFieldDescription = remember { mutableStateOf(entry.description) }
    val focusManager = LocalFocusManager.current

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
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
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = { Text(text = "Enter value") },
                        label = { Text(text = "Amount") },
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = textFieldDescription.value,
                        onValueChange = { textFieldDescription.value = it },
                        label = { Text(text = "Description") },
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {
                        val updatedEntry = Entry(
                            entry.id,
                            parseFloat(textFieldAmount.value),
                            entry.month,
                            textFieldDescription.value,
                            DateTime.now()
                        )
                        setEntry(updatedEntry)
                        setShowDialog(false)
                    }) {
                        Text(text = "Done")
                    }
                }
            }
        }
    }
}