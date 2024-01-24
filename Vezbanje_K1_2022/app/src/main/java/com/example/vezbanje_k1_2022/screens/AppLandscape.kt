package com.example.vezbanje_k1_2022.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vezbanje_k1_2022.viewmodel.CalculatorState
import com.example.vezbanje_k1_2022.viewmodel.CalculatorViewModel

@Composable
fun AppLandscape(
    list: List<CalculatorState>,
    calculatorViewModel: CalculatorViewModel,
    modifier: Modifier = Modifier,
    updateList: (List<CalculatorState>) -> Unit
) {
    Row (
        modifier = modifier
    ) {
        FirstScreen(
            list = list,
            calculatorViewModel = calculatorViewModel,
            updateList = updateList,
            changeScreen = { },
            shouldHaveButton = false,
            modifier = Modifier.weight(1f)
        )
        SecondScreen(
            list = list,
            calculatorViewModel = calculatorViewModel,
            modifier = Modifier.weight(1f)
        )
    }
}