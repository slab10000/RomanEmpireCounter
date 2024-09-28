package com.blas.romanempirecounter.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DayChangeReceiver(private val onDayChange: () -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_DATE_CHANGED) {
            // Se ejecuta cuando el día ha cambiado
            Log.i("mitag", "BroadcastReceiver: Día cambiado detectado")
            onDayChange()
        }
    }
}
