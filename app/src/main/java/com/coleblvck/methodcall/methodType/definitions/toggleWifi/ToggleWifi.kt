package com.coleblvck.methodcall.methodType.definitions.toggleWifi

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi

// Argument: listOf(Context, Boolean enabled)
val toggleWifi: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 2 && arg[0] is Context && arg[1] is Boolean) {
        val context = arg[0] as Context
        val enabled = arg[1] as Boolean

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // API 29+ requires user to manually enable via Settings panel
                val panelIntent = android.content.Intent(Settings.Panel.ACTION_WIFI)
                panelIntent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(panelIntent)
            } else {
                @Suppress("DEPRECATION")
                val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                @Suppress("DEPRECATION")
                wifiManager.isWifiEnabled = enabled
            }
        } catch (e: Exception) {
            Log.e("TOGGLE_WIFI_ERROR", e.message ?: "Could not toggle WiFi")
        }
    }
}