package com.example.klijent.baza

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klijent.MainActivity
import com.example.klijent.R
import com.example.klijent.retrofit.ObavezaRetroRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("MissingPermission")
fun makeStatusNotification(context: Context, message: String) {
    val notificationChannel = NotificationChannelCompat
        .Builder("channel_id", NotificationManagerCompat.IMPORTANCE_LOW)
        .setName("Sync notification channel")
        .setDescription("Channel giving info on database synchronization")
        .build()
    NotificationManagerCompat
        .from(context)
        .createNotificationChannel(notificationChannel)

    val notification = NotificationCompat
        .Builder(context, "channel_id")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Sync notification")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setVibrate(LongArray(0))
        .build()
    NotificationManagerCompat
        .from(context)
        .notify(23, notification)
}

@SuppressLint("MissingPermission")
fun makeStatusNotificationExtra(context: Context, message: String, obavezaId: Int = 1) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtra("destination", "DestinationB")
        putExtra("obavezaId", obavezaId)
    }
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notificationChannel = NotificationChannelCompat
        .Builder("channel_id_extra", NotificationManagerCompat.IMPORTANCE_LOW)
        .setName("Obaveze notification channel")
        .setDescription("Channel giving info on obaveze")
        .build()
    NotificationManagerCompat
        .from(context)
        .createNotificationChannel(notificationChannel)

    val notification = NotificationCompat
        .Builder(context, "channel_id_extra")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Obaveza notification")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setVibrate(LongArray(0))
        .build()
    NotificationManagerCompat
        .from(context)
        .notify(24, notification)
}

@HiltViewModel
class ObavezaViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val obavezaRepo: ObavezaRepo,
    private val obavezaRetroRepo: ObavezaRetroRepo
) : ViewModel() {
    val obaveze = obavezaRepo.obaveze
    val obavezeAsc = obavezaRepo.obavezeAsc
    val neodradjeneObaveze = obavezaRepo.neodradjeneObaveze
    val neodradjeneObavezeAsc = obavezaRepo.neodradjeneObavezeAsc
    val localCont = context

    init {
        syncBaza()
    }

    fun updateObaveza(obaveza: Obaveza) = viewModelScope.launch {
        obavezaRepo.updateObaveza(obaveza)
    }

    fun insertObaveza(obaveza: Obaveza) = viewModelScope.launch {
        obavezaRepo.insertObaveza(obaveza)
    }

    fun deleteAllObaveze() = viewModelScope.launch {
        obavezaRepo.deleteAllObaveze()
    }

    fun syncBaza() = viewModelScope.launch {
        while (isActive) {
            makeStatusNotification(localCont, "Syncing with database")
            val obv = obavezaRetroRepo.getObaveze(null)
            obv.forEach {
                insertObaveza(
                    Obaveza(
                        id = it.id,
                        fleg = it.fleg,
                        naziv = it.naziv,
                        vreme = it.vreme
                    )
                )
            }
            delay(5000)
        }
    }
}