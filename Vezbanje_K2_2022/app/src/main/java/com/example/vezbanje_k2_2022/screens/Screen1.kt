package com.example.vezbanje_k2_2022.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.vezbanje_k2_2022.database.PrimeViewModel

@Composable
fun Screen1(
    viewModel: PrimeViewModel,
    modifier: Modifier = Modifier
) {
    val rows by viewModel.rows.collectAsState(initial = listOf())
    
    LazyColumn(
        modifier = modifier
    ) {
        items(rows) {
            Text(text = "Vodic: " + it.leader + ", Prosti brojevi: " + it.first + ", " + it.second + ", " + it.third)
        }
    }
}