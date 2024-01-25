package com.example.customsensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.customsensors.ui.theme.CustomSensorsTheme
import kotlin.math.sqrt


class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var tempSensor: Sensor? = null
    private var sensorSelected: Sensor? = null

    private lateinit var mediaPlayer: MediaPlayer
    private var indicator = true
    private var acceleration = 10f
    private var currentAcceleration = SensorManager.GRAVITY_EARTH
    private var lastAcceleration = SensorManager.GRAVITY_EARTH

    private fun selectSensor(sensor: Sensor?) {
        sensorManager.unregisterListener(this)
        sensorSelected = sensor
        sensorManager.registerListener(this, sensorSelected, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val x: Float = event.values[0]
                val y: Float = event.values[1]
                val z: Float = event.values[2]
                if (indicator) {
                    val magnitude = sqrt(x * x + y * y + z * z)
                    if (magnitude < 2) mediaPlayer.start()
                }
                else {
                    lastAcceleration = currentAcceleration
                    currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                    val delta = currentAcceleration - lastAcceleration
                    acceleration = acceleration * 0.9f + delta
                    if (acceleration > 12) {
                        Toast.makeText(this, "Shake event detected", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                val curr: Float = event.values[0]
                Toast.makeText(this, "Temperature changed! $curr", Toast.LENGTH_SHORT).show()
                mediaPlayer.start()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // nothing
    }

    override fun onResume() {
        super.onResume()
        sensorSelected.let {
            sensorManager.registerListener(
                this,
                sensorSelected,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        mediaPlayer = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        setContent {
            CustomSensorsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SensorButtons()
                }
            }
        }
    }

    @Composable
    fun SensorButtons() {
        Column(modifier = Modifier.padding(16.dp)) {
            val context = LocalContext.current

            Button(
                onClick = {
                    selectSensor(accelerometer)
                    indicator = true
                    Toast.makeText(context, "Accelerometer for fall detection selected", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Detect Fall")
            }

            Button(
                onClick = {
                    selectSensor(accelerometer)
                    indicator = false
                    Toast.makeText(context, "Accelerometer for shake detection selected", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Detect Shake")
            }

            Button(
                onClick = {
                    selectSensor(tempSensor)
                    Toast.makeText(context, "Temperature sensor selected", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Detect Temperature Change")
            }
        }
    }
}

