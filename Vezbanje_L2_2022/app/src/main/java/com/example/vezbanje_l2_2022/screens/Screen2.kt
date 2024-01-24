package com.example.vezbanje_l2_2022.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Screen2(
    modifier: Modifier = Modifier,
    onClick: (buttonInfo: Int) -> Unit
) {
    Column (
        modifier = modifier
    ) {
        Button(onClick = { onClick(1) }) {
            Text(text = "First button")
        }
        Button(onClick = { onClick(2) }) {
            Text(text = "Second button")
        }
        Button(onClick = { onClick(3) }) {
            Text(text = "Third button")
        }
    }
}