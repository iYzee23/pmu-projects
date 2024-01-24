package com.example.vezba_l2.database

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZapisViewModel @Inject constructor(private val zapisRepo: ZapisRepo): ViewModel() {
    val zapisi = zapisRepo.zapisi

    val maxRedni = zapisRepo.maxRedni

    fun insert(zapis: Zapis) = viewModelScope.launch {
        zapisRepo.insert(zapis)
    }

    fun deleteAll() = viewModelScope.launch {
        zapisRepo.deleteAll()
    }
}