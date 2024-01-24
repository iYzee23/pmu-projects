package com.example.vezbanje_l3_2022

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vezbanje_l3_2022.retrofit.JokesViewModel
import com.example.vezbanje_l3_2022.ui.elements.RadioButtonWithText
import com.example.vezbanje_l3_2022.ui.theme.Vezbanje_L3_2022Theme
import dagger.hilt.android.AndroidEntryPoint

/*
    kategorija -->  https://v2.jokeapi.dev/joke/Any
                    https://v2.jokeapi.dev/joke/Programming,Miscellaneous,Dark,Pun,Spooky,Christmas
    jezik -->       https://v2.jokeapi.dev/joke/Any
                    https://v2.jokeapi.dev/joke/Any?lang=cs/de/es/fr/pt
    flegovi -->     https://v2.jokeapi.dev/joke/Any
                    https://v2.jokeapi.dev/joke/Any?blacklistFlags=nsfw,religious,political,racist,sexist,explicit
    tekst -->       https://v2.jokeapi.dev/joke/Any
                    https://v2.jokeapi.dev/joke/Any?contains=head
    broj -->        https://v2.jokeapi.dev/joke/Any
                    https://v2.jokeapi.dev/joke/Any?amount=2
*/

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // ServiceJoke
    private lateinit var serviceJoke: JokeService
    private var isBoundedServiceJoke: Boolean = false
    private val connectionToServiceJoke = object: ServiceConnection {
        override fun onServiceConnected(component: ComponentName?, service: IBinder?) {
            val binder = service as JokeService.LocalBinder
            serviceJoke = binder.getService()
            isBoundedServiceJoke = true
        }

        override fun onServiceDisconnected(component: ComponentName?) {
            isBoundedServiceJoke = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, JokeService::class.java).also {
            bindService(it, connectionToServiceJoke, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBoundedServiceJoke) {
            unbindService(connectionToServiceJoke)
            isBoundedServiceJoke = false
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val jokesViewModel: JokesViewModel by viewModels()
            val uiState by jokesViewModel.uiState.collectAsState()

            val categoryList = listOf("Any", "Programming/Dark")
            var category by rememberSaveable { mutableStateOf("Any") }

            val languageList = listOf("English", "German")
            var language by rememberSaveable { mutableStateOf("English") }

            val flagsList = listOf("Nothing", "Political/Religious")
            var flags by rememberSaveable { mutableStateOf("Nothing") }

            var text by rememberSaveable { mutableStateOf("") }
            var number by rememberSaveable { mutableStateOf("") }

            Vezbanje_L3_2022Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        categoryList.forEach {
                            RadioButtonWithText(
                                text = it,
                                selected = it == category,
                                onClick = { category = it }
                            )
                        }
                        languageList.forEach {
                            RadioButtonWithText(
                                text = it,
                                selected = it == language,
                                onClick = { language = it }
                            )
                        }
                        flagsList.forEach {
                            RadioButtonWithText(
                                text = it,
                                selected = it == flags,
                                onClick = { flags = it }
                            )
                        }
                        OutlinedTextField(
                            label = { Text(text = "Unesite rec") },
                            value = text,
                            onValueChange = { text = it }
                        )
                        OutlinedTextField(
                            label = { Text(text = "Unesite broj") },
                            value = number,
                            onValueChange = { number = it }
                        )
                        Button(onClick = {
                            jokesViewModel.update(
                                category = if (category == "Any") category else "Programming,Dark",
                                lang = if (language == "English") null else "lang=de",
                                flags = if (flags == "Nothing") null else "blacklistFlags=political,religious",
                                text = if (text == "") null else "contains=$text",
                                number = if (number == "") null else "amount=$number"
                            )
                            serviceJoke.startProcess(5, jokesViewModel)
                        }) {
                            Text(text = "Zapocni periodicno dohvatanje viceva")
                        }
                        Button(onClick = { serviceJoke.stopProcess() }) {
                            Text(text = "Zaustavi periodicno dohvatanje viceva")
                        }
                        if (uiState.oneJoke != null) {
                            when (uiState.oneJoke!!.type) {
                                "twopart" -> Text(text = uiState.oneJoke!!.setup + "?\n" + uiState.oneJoke!!.delivery)
                                "single" -> Text(text = uiState.oneJoke!!.joke + "")
                            }
                        }
                        else if (uiState.moreJokes != null) {
                            LazyColumn {
                                items(uiState.moreJokes!!) {
                                    when (it.type) {
                                        "twopart" -> Text(text = it.setup + "?\n\n" + it.delivery + "\n-------------------\n\n")
                                        "single" -> Text(text = it.joke + "\n\n")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}