package com.example.vezbanje_l3_2022

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.ViewModel
import com.example.vezbanje_l3_2022.retrofit.JokesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class JokeService : LifecycleService() {

    companion object {
        const val NOTIFICATION_CHANNEL = "General notification channel"
        const val NOTIFICATION_ID = 23
    }

    // ovu fju pozivamo uvek kad imamo notifikaciju
    // dakle, svaki put i pravimo novi kanal
    // ocigledno je to okej, tako da uvek po ovom principu funkcionisemo
    @SuppressLint("MissingPermission")
    fun makeStatusNotification(context: Context, message: String) {
        val notificationChannel = NotificationChannelCompat
                .Builder(NOTIFICATION_CHANNEL, NotificationManagerCompat.IMPORTANCE_HIGH)
                .setName(NOTIFICATION_CHANNEL)
                .setDescription(NOTIFICATION_CHANNEL)
                .build()
        NotificationManagerCompat
            .from(context)
            .createNotificationChannel(notificationChannel)

        val notification = NotificationCompat
            .Builder(context, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(NOTIFICATION_CHANNEL)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(1))
            .build()
        NotificationManagerCompat
            .from(context)
            .notify(NOTIFICATION_ID, notification)
    }

    inner class LocalBinder : Binder() {
        fun getService(): JokeService = this@JokeService
    }

    private val binder = LocalBinder()
    private var job: Job? = null

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return binder
    }

    fun startProcess(seconds: Int, jokeViewModel: JokesViewModel) {
        if (job == null) {
            makeStatusNotification(this, "New notification")
            job = CoroutineScope(SupervisorJob() + Dispatchers.Default).launch {
                while (isActive) {
                    jokeViewModel.refresh()
                    delay(seconds * 1000L)
                }
            }
        }
    }

    fun stopProcess() {
        job?.cancel()
        job = null
    }

}