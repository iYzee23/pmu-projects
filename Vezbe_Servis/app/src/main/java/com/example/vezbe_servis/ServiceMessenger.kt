package com.example.vezbe_servis

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.widget.Toast
import androidx.lifecycle.LifecycleService

class ServiceMessenger : LifecycleService() {

    companion object {
        const val MESSAGE_KEY = 23
    }

    private lateinit var messenger: Messenger

    class MessageHandler(context: Context) : Handler(Looper.getMainLooper()) {
        private val appContext: Context = context.applicationContext

        override fun handleMessage(message: Message) {
            when (message.what) {
                MESSAGE_KEY -> {
                    val toast = Toast.makeText(appContext, "Messenger", Toast.LENGTH_SHORT)
                    toast.show()
                }
                else -> {
                    super.handleMessage(message)
                }
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        messenger = Messenger(MessageHandler(this))
        return messenger.binder
    }

}