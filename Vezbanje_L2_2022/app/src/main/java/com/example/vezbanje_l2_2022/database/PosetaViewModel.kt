package com.example.vezbanje_l2_2022.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vezbanje_l2_2022.MainApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PosetaViewModel @Inject constructor(private val posetaRepo: PosetaRepo) : ViewModel() {
    val posete = posetaRepo.posete

    fun insert(poseta: Poseta) = viewModelScope.launch { posetaRepo.insert(poseta) }

    fun deleteAll() = viewModelScope.launch { posetaRepo.deleteAll() }
}