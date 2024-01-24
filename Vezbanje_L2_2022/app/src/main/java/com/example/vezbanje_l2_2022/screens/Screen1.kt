package com.example.vezbanje_l2_2022.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.vezbanje_l2_2022.database.PosetaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen1(
    viewModel: PosetaViewModel,
    modifier: Modifier = Modifier
) {
    val posete by viewModel.posete.collectAsState(initial = listOf())
    
    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Screen1") },
                actions = {
                    IconButton(onClick = {
                        viewModel.deleteAll()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column (
            modifier = modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn (
                modifier = modifier
            ) {
                items(posete) {pos ->
                    Text(text = "Redni broj: " + pos.id + ", Destinacija: " + pos.destinacija)
                }
            }
        }
    }
}