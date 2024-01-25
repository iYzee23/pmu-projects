package com.example.predavanja_broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ReceiverAsync : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult: PendingResult = goAsync()

        CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {
            try {
                StringBuilder().apply {
                    append("Receiver: Async\n")
                    append("Action: ${intent.action}\n")
                    append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
                    toString().also { log ->
                        Toast.makeText(context, log, Toast.LENGTH_LONG).show()
                    }
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
            finally {
                pendingResult.finish()
            }
        }
    }

}