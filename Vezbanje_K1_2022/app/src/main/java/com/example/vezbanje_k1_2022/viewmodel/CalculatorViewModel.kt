package com.example.vezbanje_k1_2022.viewmodel

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import kotlinx.parcelize.Parcelize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Parcelize
data class CalculatorState (
    val firstValue: String = "",
    val secondValue: String = "",
    val operator: String = "",
    val result: String = "",
    val ok: Boolean = false
) : Parcelable

class CalculatorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalculatorState())
    val uiState = _uiState.asStateFlow()

    fun setFirstValue(firstValue: String) {
        _uiState.update { it.copy(firstValue = firstValue) }
        calculateResult()
    }

    fun setSecondValue(secondValue: String) {
        _uiState.update { it.copy(secondValue = secondValue) }
        calculateResult()
    }

    fun setOperator(operator: String) {
        _uiState.update { it.copy(operator = operator) }
        calculateResult()
    }

    private fun calculateResult(): Unit {
        val operator = uiState.value.operator;
        val firstValue = uiState.value.firstValue;
        val secondValue = uiState.value.secondValue;

        if (operator == "") _uiState.update { it.copy(result = "You must enter the operator.") }
        else if (firstValue == "" || secondValue == "") _uiState.update { it.copy(result = "You must enter both operands.") }
        else {
            val first = firstValue.toDoubleOrNull()
            val second = secondValue.toDoubleOrNull()
            if (first == null || second == null) _uiState.update { it.copy(result = "You must enter valid operands.") }
            else if (second == 0.0 && operator == "/") _uiState.update { it.copy(result = "You cannot divide with 0.") }
            else {
                when(operator) {
                    "+" -> _uiState.update { it.copy(result = String.format("%.2f", first + second).trimEnd('0', '.', ',')) }
                    "-" -> _uiState.update { it.copy(result = String.format("%.2f", first - second).trimEnd('0', '.', ',')) }
                    "*" -> _uiState.update { it.copy(result = String.format("%.2f", first * second).trimEnd('0', '.', ',')) }
                    "/" -> _uiState.update { it.copy(result = String.format("%.2f", first / second).trimEnd('0', '.', ',')) }
                }
                _uiState.update { it.copy(ok = true) }
                return
            }
        }
        _uiState.update { it.copy(ok = false) }
    }
}