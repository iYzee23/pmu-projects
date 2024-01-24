@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.vezbanje_l1_2022

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.vezbanje_l1_2022.ui.theme.Vezbanje_L1_2022Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vezbanje_L1_2022Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    val context = LocalContext.current as Activity
    val radioOptions = context.resources.getStringArray(R.array.radio_options).toList()

    var firstValue by rememberSaveable { mutableStateOf("") }
    var secondValue by rememberSaveable { mutableStateOf("") }
    var selectedRadio by rememberSaveable { mutableStateOf("") }
    var labelText by rememberSaveable { mutableStateOf("") }

    fun calculate(): String {
        return if (selectedRadio == "") {
            context.getString(R.string.not_selected_operation)
        }
        else if (firstValue == "" || secondValue == "") {
            context.getString(R.string.values_not_entered)
        }
        else {
            val fVal = firstValue.toDoubleOrNull()
            val sVal = secondValue.toDoubleOrNull()
            if (fVal == null || sVal == null) context.getString(R.string.values_not_numbers)
            else if (sVal == 0.0 && selectedRadio == "/") context.getString(R.string.division_by_zero)
            else when (selectedRadio) {
                "+" -> String.format("%.2f", fVal + sVal).trimEnd('0').trimEnd('.').trimEnd(',')
                "-" -> String.format("%.2f", fVal - sVal).trimEnd('0').trimEnd('.').trimEnd(',')
                "*" -> String.format("%.2f", fVal * sVal).trimEnd('0').trimEnd('.').trimEnd(',')
                else -> String.format("%.2f", fVal / sVal).trimEnd('0').trimEnd('.').trimEnd(',')
            }
        }
    }

    Column (
        modifier = modifier.padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = firstValue,
            onValueChange = { firstValue = it },
            label = { Text(text = stringResource(R.string.first_value)) },
            placeholder = { Text(text = stringResource(R.string.zero)) },
            modifier = Modifier.background(Color.White)
        )
        OutlinedTextField(
            value = secondValue,
            onValueChange = { secondValue = it },
            label = { Text(text = stringResource(R.string.second_value)) },
            placeholder = { Text(text = stringResource(R.string.zero)) },
            modifier = Modifier.background(Color.White)
        )
        Row (
            modifier = Modifier.selectableGroup()
        ) {
            radioOptions.forEach {
                Row (
                    modifier = Modifier
                        .selectable(
                            selected = (it == selectedRadio),
                            onClick = { selectedRadio = it },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    RadioButton(
                        selected = (it == selectedRadio), 
                        onClick = null,
                        modifier = Modifier.padding(start = 10.dp, end = 5.dp)
                    )
                    Text(
                        text = it,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
        Button(onClick = { labelText = calculate() }) {
            Text(text = stringResource(R.string.calculate))
        }
        Text(text = labelText)
    }
}