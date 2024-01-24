package com.example.klijent.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.klijent.DateTimeUtil
import com.example.klijent.baza.ObavezaViewModel
import com.example.klijent.retrofit.ObavezaRetro
import com.example.klijent.retrofit.ObavezaRetroViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenC (
    obavezaViewModel: ObavezaViewModel,
    obavezaRetroViewModel: ObavezaRetroViewModel,
    modifier: Modifier = Modifier,
    onClickFirst: () -> Unit
) {
    val uiState by obavezaRetroViewModel.uiState.collectAsState()
    var nazivObaveze by rememberSaveable { mutableStateOf("") }
    var opisObaveze by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // date
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // time
    val hour = calendar.get(Calendar.HOUR)
    val minute = calendar.get(Calendar.MINUTE)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            obavezaRetroViewModel.setDate(y, m, d)
        },
        year, month, day,
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, h: Int, m: Int ->
            obavezaRetroViewModel.setTime(h, m)
        },
        hour, minute, true
    )

    val dateInteractionSource = remember { MutableInteractionSource() }
    val timeInteractionSource = remember { MutableInteractionSource() }
    if (dateInteractionSource.collectIsPressedAsState().value) {
        datePickerDialog.show()
    }
    if (timeInteractionSource.collectIsPressedAsState().value) {
        timePickerDialog.show()
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Todo List - Dodavanje obaveze") },
                navigationIcon = {
                    IconButton(
                        onClick = { onClickFirst() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column (
            modifier = Modifier.padding(it)
        ) {
            OutlinedTextField(
                label = { Text(text = "Unesite naziv obaveze") },
                value = nazivObaveze,
                onValueChange = { nazivObaveze = it },
                singleLine = true
            )
            OutlinedTextField(
                label = { Text(text = "Izaberite datum obaveze") },
                value = uiState.vreme.let { if (it != null) DateTimeUtil.formatDate(it).split('T')[0] else "" },
                onValueChange = {},
                trailingIcon = {
                    Icon(imageVector = Icons.Default.EditCalendar, contentDescription = null)
                },
                readOnly = true,
                interactionSource = dateInteractionSource
            )
            OutlinedTextField(
                label = { Text(text = "Izaberite vreme obaveze") },
                value = uiState.vreme.let { if (it != null) DateTimeUtil.formatDate(it).split('T')[1] else "" },
                onValueChange = {},
                trailingIcon = {
                    Icon(imageVector = Icons.Default.EditNote, contentDescription = null)
                },
                readOnly = true,
                interactionSource = timeInteractionSource
            )
            OutlinedTextField(
                label = { Text(text = "Unesite opis obaveze") },
                value = opisObaveze,
                onValueChange = { opisObaveze = it },
                singleLine = false,
                modifier = Modifier.heightIn(100.dp)
            )
            Button(
                onClick = {
                    obavezaRetroViewModel.dodajObavezu(nazivObaveze, opisObaveze)
                    onClickFirst()
                },
                enabled = opisObaveze != "" && nazivObaveze != ""
            ) {
                Text(text = "Unesite obavezu")
            }
        }
    }
}