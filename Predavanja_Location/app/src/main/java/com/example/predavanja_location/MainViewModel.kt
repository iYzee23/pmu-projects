package com.example.predavanja_location

import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _uiBaseLocationState = MutableStateFlow(String())
    private val _uiExtendedLocationState = MutableStateFlow(String())

    val uiBaseLocationState = _uiBaseLocationState.asStateFlow()
    val uiExtendedLocationState = _uiExtendedLocationState.asStateFlow()

    private val MAX_ADDRESSES = 50
    lateinit var geocoder: Geocoder

    private fun getBaseFromLocation(location: Location?): String {
        val result: String
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            val altitude = location.altitude
            val provider = location.provider
            val accuracy = location.accuracy
            val time = location.time
            val speed = location.speed
            getExtendedFromLocation(latitude, longitude)
            result = "Latitude: $latitude\n " +
                    "Longitude: $longitude\n " +
                    "Altitude: $altitude\n " +
                    "Provider: $provider\n " +
                    "Accuracy: $accuracy meter(s)\n " +
                    "Time: $time\n " +
                    "Speed: $speed m/s"
        }
        else {
            result = "Unknown location"
        }
        return result
    }

    private fun getExtendedFromLocation(latitude: Double, longitude: Double) {
        if (Geocoder.isPresent()) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(latitude, longitude, MAX_ADDRESSES) { addresses ->
                        val sb = StringBuilder()
                        for (address in addresses) {
                            val cn = address.countryName
                            val sa = address.subAdminArea
                            val lo = address.locality
                            val th = address.thoroughfare
                            val fn = address.featureName
                            sb.append("$cn, $sa, $lo, $th, $fn\n")
                        }
                        _uiExtendedLocationState.update { sb.toString() }
                    }
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun update(location: Location) {
        val result = getBaseFromLocation(location)
        _uiBaseLocationState.update { result }
    }

}