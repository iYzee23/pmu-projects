package com.example.vezbe_servis

import android.app.Notification
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServiceForeground : LifecycleService() {

    companion object {
        const val NOTIFICATION_CHANNEL = "General notification channel"
        const val NOTIFICATION_ID = 23
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, getNotification())
        lifecycle.coroutineScope.launch (Dispatchers.Main) {
            repeat(100) {
                val toast = Toast.makeText(this@ServiceForeground, "Foreground: $it", Toast.LENGTH_SHORT)
                toast.show()
                delay(3000)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannelCompat
            .Builder(NOTIFICATION_CHANNEL, NotificationManagerCompat.IMPORTANCE_LOW)
            .setName("General notification channel")
            .setDescription("Here will be listed general notification")
            .build()
        NotificationManagerCompat
            .from(this)
            .createNotificationChannel(notificationChannel)
    }

    private fun getNotification() : Notification {
        val notificationBuilder = NotificationCompat
            .Builder(this, NOTIFICATION_CHANNEL).apply {
                // mora ovo da se doda da bi se prikazala notifikacija lepo
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setContentTitle("Title")
                setContentText("Text")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    foregroundServiceBehavior = Notification.FOREGROUND_SERVICE_IMMEDIATE
                }
            }
        return notificationBuilder.build()
    }

}