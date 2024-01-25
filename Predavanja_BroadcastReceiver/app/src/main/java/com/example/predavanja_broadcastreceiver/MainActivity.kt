package com.example.predavanja_broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.predavanja_broadcastreceiver.ui.theme.Predavanja_BroadcastReceiverTheme

class MainActivity : ComponentActivity() {

    private var br: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        br = ReceiverBattery()
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val listenToBroadcastFromOtherApps = false
        val receiverFlags =
            if (listenToBroadcastFromOtherApps) ContextCompat.RECEIVER_EXPORTED
            else ContextCompat.RECEIVER_NOT_EXPORTED
        ContextCompat.registerReceiver(this, br, filter, receiverFlags)

        setContent {
            Predavanja_BroadcastReceiverTheme {
                MainContent()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(br)
    }
}

// ovo ispod je vezano za broadcaste registrovane staticki

const val BROADCAST_NAME = "internal_broadcast"

fun sendBroadcast(context: Context) {
    val intent = Intent(BROADCAST_NAME)
    intent.`package` = "com.example.predavanja_broadcastreceiver"
    intent.putExtra("KEY", 5)
    context.sendBroadcast(intent)
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Surface (
        modifier = modifier.fillMaxSize(),
        color = Color.Gray
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { sendBroadcast(context) }, modifier = Modifier.width(width = 250.dp)
            ) {
                Text(text = "Send async/internal broadcast")
            }
        }
    }
}