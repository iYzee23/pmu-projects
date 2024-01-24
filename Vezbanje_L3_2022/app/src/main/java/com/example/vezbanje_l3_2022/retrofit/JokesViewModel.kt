package com.example.vezbanje_l3_2022.retrofit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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

data class UiState(
    val category: String = "Any",
    val lang: String? = null,
    val flags: String? = null,
    val text: String? = null,
    val number: String? = null,
    val moreJokes: List<Joke>? = null,
    val oneJoke: Joke? = null,
)

@HiltViewModel
class JokesViewModel @Inject constructor(private val jokesRepo: JokesRepo) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun update(category: String, lang: String?, flags: String?, text: String?, number: String?) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                category = category,
                lang = lang,
                flags = flags,
                text = text,
                number = number
            )
        }
    }

    fun refresh() = viewModelScope.launch {
        var url = uiState.value.category
        var first = false

        val lang = uiState.value.lang
        val flags = uiState.value.flags
        val text = uiState.value.text
        val number = uiState.value.number

        if (lang != null || flags != null || text != null || number != null) {
            url += "?"

            if (lang != null) {
                if (!first) url += "&"
                else first = true
                url += lang
            }

            if (flags != null) {
                if (!first) url += "&"
                else first = true
                url += flags
            }

            if (text != null) {
                if (!first) url += "&"
                else first = true
                url += text
            }

            if (number != null) {
                if (!first) url += "&"
                else first = true
                url += number
            }
        }

        if (number != null) {
            val response = jokesRepo.getMoreJokes(url)
            _uiState.update {
                it.copy(
                    moreJokes = response.jokes,
                    oneJoke = null
                )
            }
        }
        else {
            val response = jokesRepo.getOneJoke(url)
            _uiState.update {
                it.copy(
                    moreJokes = null,
                    oneJoke = response
                )
            }
        }
    }

}