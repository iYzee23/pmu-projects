package com.example.vezbanje_k1_2022.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.vezbanje_k1_2022.composables.Spinner
import com.example.vezbanje_k1_2022.viewmodel.CalculatorState
import com.example.vezbanje_k1_2022.viewmodel.CalculatorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(
    list: List<CalculatorState>,
    calculatorViewModel: CalculatorViewModel,
    changeScreen: () -> Unit,
    shouldHaveButton: Boolean,
    modifier: Modifier = Modifier,
    updateList: (List<CalculatorState>) -> Unit
) {
    val uiState by calculatorViewModel.uiState.collectAsState()
    val operatorOptions = listOf("+", "-", "*", "/")

    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.firstValue,
            onValueChange = calculatorViewModel::setFirstValue,
            label = { Text(text = "First operand") },
        )
        OutlinedTextField(
            value = uiState.secondValue,
            onValueChange = calculatorViewModel::setSecondValue,
            label = { Text(text = "Second operand") }
        )
        Spinner(
            value = if (uiState.operator != "") uiState.operator else "Choose operator",
            onSelect = calculatorViewModel::setOperator,
            options = operatorOptions
        )
        Text(
            text = uiState.result,
            color = if (uiState.ok) Color.Black else Color.Red
        )
        Button(
            // iz nekog razloga ova provera sasvim lepo radi
            // ne dodavati equals u data class...
            onClick = { if (!list.contains(uiState)) updateList(list + uiState) },
            enabled = uiState.ok
        ) {
            Text(text = "Save the active result")
        }
        if (shouldHaveButton) {
            Button(
                onClick = changeScreen
            ) {
                Text(text = "Go to second screen.")
            }
        }
    }
}