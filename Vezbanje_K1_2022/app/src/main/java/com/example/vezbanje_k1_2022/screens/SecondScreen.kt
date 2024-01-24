package com.example.vezbanje_k1_2022.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vezbanje_k1_2022.viewmodel.CalculatorState
import com.example.vezbanje_k1_2022.viewmodel.CalculatorViewModel

@Composable
fun SecondScreen(
    list: List<CalculatorState>,
    calculatorViewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            items(list) {
                Text(
                    text = it.firstValue + " " + it.operator + " " + it.secondValue + " = " + it.result,
                    modifier = Modifier.clickable {
                        calculatorViewModel.setFirstValue(it.firstValue)
                        calculatorViewModel.setSecondValue(it.secondValue)
                        calculatorViewModel.setOperator(it.operator)
                    }
                )
            }
        }
    }
}