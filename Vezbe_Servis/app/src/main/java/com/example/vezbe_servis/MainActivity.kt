package com.example.vezbe_servis

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.vezbe_servis.ui.theme.Vezbe_ServisTheme

class MainActivity : ComponentActivity() {

    // ServiceLocalBinder
    private lateinit var serviceLocalBinder: ServiceLocalBinder
    private var isBoundedServiceLocalBinder: Boolean = false
    private val connectionToServiceLocalBinder = object: ServiceConnection {
        override fun onServiceConnected(component: ComponentName?, service: IBinder?) {
            val binder = service as ServiceLocalBinder.LocalBinder
            serviceLocalBinder = binder.getService()
            isBoundedServiceLocalBinder = true
        }

        override fun onServiceDisconnected(component: ComponentName?) {
            isBoundedServiceLocalBinder = false
        }
    }

    // ServiceMessenger
    private lateinit var serviceMessenger: Messenger
    private var isBoundedServbiceMessenger: Boolean = false
    private val connectionToServiceMessenger = object: ServiceConnection {
        override fun onServiceConnected(component: ComponentName?, service: IBinder?) {
            serviceMessenger = Messenger(service)
            isBoundedServbiceMessenger = true
        }

        override fun onServiceDisconnected(component: ComponentName?) {
            isBoundedServbiceMessenger = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, ServiceLocalBinder::class.java).also {
            bindService(it, connectionToServiceLocalBinder, Context.BIND_AUTO_CREATE)
        }
        Intent(this, ServiceMessenger::class.java).also {
            bindService(it, connectionToServiceMessenger, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBoundedServiceLocalBinder) {
            unbindService(connectionToServiceLocalBinder)
            isBoundedServiceLocalBinder = false
        }
        if (isBoundedServbiceMessenger) {
            unbindService(connectionToServiceMessenger)
            isBoundedServbiceMessenger = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) {
                if (it) {
                    startService(Intent().apply {
                        setClass(
                            this@MainActivity,
                            ServiceForeground::class.java
                        )
                    })
                }
            }

            Vezbe_ServisTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    ) {
                        Button(onClick = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                if (ContextCompat.checkSelfPermission(
                                        this@MainActivity,
                                        Manifest.permission.POST_NOTIFICATIONS
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    startService(Intent().apply {
                                        setClass(
                                            this@MainActivity,
                                            ServiceForeground::class.java,
                                        )
                                    })
                                }
                            } else {
                                startService(Intent().apply {
                                    setClass(
                                        this@MainActivity,
                                        ServiceForeground::class.java,
                                    )
                                })
                            }
                        }) {
                            Text(text = "Start Foreground service")
                        }
                        Spacer(modifier = Modifier.padding(16.dp))
                        Button(onClick = { serviceLocalBinder.showToast() }) {
                            Text(text = "Start LocalBinded service")
                        }
                        Spacer(modifier = Modifier.padding(16.dp))
                        Button(onClick = {
                            val message = Message.obtain(null, ServiceMessenger.MESSAGE_KEY, 0, 0)
                            try { serviceMessenger.send(message) }
                            catch (e: Exception) { e.printStackTrace() }
                        }) {
                            Text(text = "Start Messenger service")
                        }
                    }
                }
            }
        }
    }
}