package com.example.klijent.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.klijent.DateTimeUtil
import com.example.klijent.baza.ObavezaViewModel
import com.example.klijent.retrofit.ObavezaRetroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenB (
    obavezaViewModel: ObavezaViewModel,
    obavezaRetroViewModel: ObavezaRetroViewModel,
    obavezaId: Int,
    modifier: Modifier = Modifier,
    onClickFirst: () -> Unit
) {
    obavezaRetroViewModel.getObavezaJ(obavezaId)
    val obavezeBaza by obavezaViewModel.obaveze.collectAsState(initial = listOf())
    val retroUiState by obavezaRetroViewModel.uiState.collectAsState()
    var obavezajBaza by remember { mutableStateOf(obavezeBaza.find { it.id == obavezaId }) }
    var obavezajRetro by remember { mutableStateOf(retroUiState.obavezaj) }
    var ind by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(obavezeBaza, retroUiState) {
        obavezajBaza = obavezeBaza.find { it.id == obavezaId }
        obavezajRetro = retroUiState.obavezaj
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Todo List - Jedna obaveza") },
                navigationIcon = {
                    IconButton(
                        onClick = { onClickFirst() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier.padding(it)
        ) {
            if (obavezajBaza != null) {
                Text(text = "Naziv: ${obavezajBaza!!.naziv}")
                Text(text = "Vreme: ${DateTimeUtil.formatDate(obavezajBaza!!.vreme)}")
            }
            if (obavezajRetro != null) {
                Text(text = "Opis: ${obavezajRetro!!.opis}")
                Button(
                    onClick = {
                        obavezaRetroViewModel.azurirajObavezu(obavezaId)
                        ind = true
                    },
                    enabled = !ind
                ) {
                    Text(text = "Azuriraj obavezu")
                }
            }
        }
    }
}