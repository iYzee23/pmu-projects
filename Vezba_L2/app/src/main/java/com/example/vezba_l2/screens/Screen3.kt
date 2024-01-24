package com.example.vezba_l2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen3(
    num: Int,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        OutlinedTextField(
            value = num.toString(),
            onValueChange = {},
            enabled = false
        )
        IconButton(
            onClick = { navHostController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
    }
}