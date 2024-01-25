package com.example.vezbe_servis

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import androidx.lifecycle.LifecycleService

class ServiceLocalBinder : LifecycleService() {

    inner class LocalBinder : Binder() {
        fun getService(): ServiceLocalBinder = this@ServiceLocalBinder
    }

    private val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return binder
    }

    fun showToast() {
        val toast = Toast.makeText(this, "Binder", Toast.LENGTH_SHORT)
        toast.show()
    }

}