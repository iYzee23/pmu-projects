package com.example.vezba_l2.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.vezba_l2.database.ZapisViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen1(
    zapisViewModel: ZapisViewModel,
    modifier: Modifier = Modifier
) {
    val zapisi by zapisViewModel.zapisi.collectAsState(initial = listOf())
    
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Screen1") },
                actions = { OptionsMenu(zapisViewModel) }
            )
        }
    ) {
        LazyColumn(
            modifier = modifier.padding(it)
        ) {
            items(zapisi) {
                Text(text = "Redni broj: " + it.redniBrojPosete + ", Destinacija: " + it.imeDestinacije)
            }
        }
    }
}

@Composable
fun OptionsMenu(
    zapisViewModel: ZapisViewModel,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            imageVector = Icons.Default.MoreHoriz,
            contentDescription = null
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = "Delete") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            },
            onClick = {
                zapisViewModel.deleteAll()
                expanded = false
            }
        )
    }
}