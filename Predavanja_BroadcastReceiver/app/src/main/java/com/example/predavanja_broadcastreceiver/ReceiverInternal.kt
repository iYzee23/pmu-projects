package com.example.predavanja_broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReceiverInternal : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val number = intent.getIntExtra("KEY", 0)
        StringBuilder().apply {
            append("Receiver: Internal $number\n")
            append("Action: ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also { log ->
                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
            }
        }
    }

}