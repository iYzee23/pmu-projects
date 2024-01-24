package com.example.vezbanje_k2_2022.database

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vezbanje_k2_2022.Processing
import com.example.vezbanje_k2_2022.run

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrimeViewModel @Inject constructor(private val primeRepo: PrimeRepo): ViewModel() {
    val rows: Flow<List<Prime>> = primeRepo.rows

    fun insert(prime: Prime) = viewModelScope.launch {
        primeRepo.insert(prime)
    }

    fun exists(leader: Int) = viewModelScope.launch {
        val curr = primeRepo.exists(leader)
        if (curr != null) {
            process.leader = curr.leader
            process.first = curr.first
            process.second = curr.second
            process.third = curr.third
        }
        else {
            process.leader = leader
            process.first = 0
            process.second = 0
            process.third = 0
            run()
        }
    }

    var process by mutableStateOf(Processing())

    fun run() = viewModelScope.launch {
        process.run()
        insert(Prime(
            leader = process.leader,
            first = process.first,
            second = process.second,
            third = process.third
        ))
    }
}