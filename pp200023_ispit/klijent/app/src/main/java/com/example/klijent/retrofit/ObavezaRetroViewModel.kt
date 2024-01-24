package com.example.klijent.retrofit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klijent.baza.makeStatusNotification
import com.example.klijent.baza.makeStatusNotificationExtra
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

data class UiState(
    val obaveze: List<ObavezaRetro> = listOf(),
    val obavezaj: ObavezaRetro? = null,
    val neodradjeneObaveze: List<ObavezaRetro> = listOf(),
    val neodradjenaObavezaj: ObavezaRetro? = null,
    val vreme: Date = Date()
)

@HiltViewModel
class ObavezaRetroViewModel @Inject constructor(private val obavezaRetroRepo: ObavezaRetroRepo) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun setDate(year: Int, month: Int, day: Int) {
        val updatedCalendar = Calendar.getInstance().apply {
            time = _uiState.value.vreme
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        _uiState.update { it.copy(vreme = updatedCalendar.time) }
    }

    fun setTime(hour: Int, minute: Int) {
        val updatedCalendar = Calendar.getInstance().apply {
            time = _uiState.value.vreme
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        _uiState.update { it.copy(vreme = updatedCalendar.time) }
    }

    fun getObaveze() = viewModelScope.launch {
        val ob = obavezaRetroRepo.getObaveze(null)
        _uiState.update { it.copy(obaveze = ob) }
    }

    fun getNeodradjeneObaveze() = viewModelScope.launch {
        val ob = obavezaRetroRepo.getNeodradjeneObaveze(null)
        _uiState.update { it.copy(neodradjeneObaveze = ob) }
    }

    fun getObavezaJ(id: Int) = viewModelScope.launch {
        val ob = obavezaRetroRepo.getObaveze(id)
        if (ob.isNotEmpty()) _uiState.update { it.copy(obavezaj = ob[0]) }
    }

    fun getNeodradjenaObavezaJ(id: Int) = viewModelScope.launch {
        val ob = obavezaRetroRepo.getNeodradjeneObaveze(id)
        if (ob.isNotEmpty()) _uiState.update { it.copy(neodradjenaObavezaj = ob[0]) }
    }

    fun azurirajObavezu(id: Int) = viewModelScope.launch {
        obavezaRetroRepo.azurirajObavezu(id)
    }

    fun dodajObavezu(naziv: String, opis: String) = viewModelScope.launch {
        val ob = obavezaRetroRepo.getObaveze(null)
        val maxId = ob.maxByOrNull { it.id }?.id ?: 0
        obavezaRetroRepo.dodajObavezu(
            ObavezaRetro(
                id = maxId + 1,
                fleg = false,
                naziv = naziv,
                vreme = uiState.value.vreme,
                opis = opis
            )
        )
    }

    fun createNotificationForNearestTask(context: Context) = viewModelScope.launch {
        val ob = obavezaRetroRepo.getNeodradjeneObaveze(null)
        val idd = ob.maxByOrNull { it.id }?.id ?: 1
        makeStatusNotificationExtra(context, "Neodradjena obaveza", idd)
    }
}