package com.example.vezbanje_k2_2022.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vezbanje_k2_2022.database.Prime
import com.example.vezbanje_k2_2022.database.PrimeViewModel
import com.example.vezbanje_k2_2022.progress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen2(
    viewModel: PrimeViewModel,
    modifier: Modifier = Modifier
) {
    var value by rememberSaveable { mutableStateOf("") }
    
    Column (
        modifier = modifier
    ) {
        TextField(
            value = value,
            onValueChange = { value = it },
            label = { Text(text = "Unesite vodic") }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        LinearProgressIndicator(progress = viewModel.process.progress / 100)
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = "Progres: " + viewModel.process.progress + "% ")
        if (viewModel.process.progress == 100.00F) {
            Text(text =
                "Prvi: " + viewModel.process.first +
                ", Drugi: " + viewModel.process.second +
                ", Treci: " + viewModel.process.third
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            onClick = { viewModel.exists(value.toInt()) },
            enabled = value != ""
        ) {
            Text(text = "Zapocni trazenje sekvence")
        }
    }
}