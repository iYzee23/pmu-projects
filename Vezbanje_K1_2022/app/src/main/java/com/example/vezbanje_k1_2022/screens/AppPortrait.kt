package com.example.vezbanje_k1_2022.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vezbanje_k1_2022.viewmodel.CalculatorState
import com.example.vezbanje_k1_2022.viewmodel.CalculatorViewModel

@Composable
fun AppPortrait(
    list: List<CalculatorState>,
    calculatorViewModel: CalculatorViewModel,
    screen: Int,
    onChangeScreen: () -> Unit,
    modifier: Modifier = Modifier,
    updateList: (List<CalculatorState>) -> Unit
) {

    if (screen == 1) {
        FirstScreen(
            list = list,
            calculatorViewModel = calculatorViewModel,
            updateList = updateList,
            changeScreen = onChangeScreen,
            shouldHaveButton = true
        )
    }
    else {
        SecondScreen(
            list = list,
            calculatorViewModel = calculatorViewModel
        )
    }
}