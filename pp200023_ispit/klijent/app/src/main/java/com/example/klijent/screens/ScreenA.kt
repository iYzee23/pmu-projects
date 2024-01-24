package com.example.klijent.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.klijent.DateTimeUtil
import com.example.klijent.baza.ObavezaViewModel
import com.example.klijent.retrofit.ObavezaRetroViewModel
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenA (
    obavezaViewModel: ObavezaViewModel,
    obavezaRetroViewModel: ObavezaRetroViewModel,
    modifier: Modifier = Modifier,
    onClickSecond: (obaveza: Int) -> Unit,
    onClickThird: () -> Unit
) {
    obavezaRetroViewModel.getNeodradjeneObaveze()
    val retroUiState by obavezaRetroViewModel.uiState.collectAsState()
    var neodrObaveze = retroUiState.neodradjeneObaveze
    var neodrObavezeAsc = neodrObaveze.sortedBy { it.vreme }
    var prikazObaveze by remember { mutableStateOf(neodrObaveze) }
    var ind by remember { mutableStateOf(false) }

    LaunchedEffect(retroUiState) {
        prikazObaveze = if (ind) neodrObavezeAsc else neodrObaveze
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "ToDo List") },
                actions = {
                    if (ind) {
                        IconButton(
                            onClick = {
                                obavezaRetroViewModel.getNeodradjeneObaveze()
                                prikazObaveze = neodrObavezeAsc
                                ind = !ind
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ToggleOn,
                                contentDescription = null
                            )
                        }
                    }
                    else {
                        IconButton(
                            onClick = {
                                obavezaRetroViewModel.getNeodradjeneObaveze()
                                prikazObaveze = neodrObaveze
                                ind = !ind
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ToggleOff,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier,
                onClick = { onClickThird() }
            ) {
                Icon(
                    imageVector = Icons.Default.AddCard,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(prikazObaveze) {
                Card(
                    modifier = modifier
                        .padding(10.dp)
                        .clickable { onClickSecond(it.id) }
                ) {
                    Column(
                        modifier = modifier
                    ) {
                        val color =
                            if (it.vreme.compareTo(Date()) < 0) Color(0f, 1f, 0f, 1f)
                            else Color(1f, 0f, 0f, 0.5f)
                        Text(text = "Obaveza: ${it.naziv}", color = color)
                        Text(
                            text = "Vreme: ${DateTimeUtil.formatDate(it.vreme)}",
                            color = color
                        )
                    }
                }
            }
        }
    }
}