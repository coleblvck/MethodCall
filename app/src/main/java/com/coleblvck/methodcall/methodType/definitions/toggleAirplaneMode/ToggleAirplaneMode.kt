package com.coleblvck.methodcall.methodType.definitions.toggleAirplaneMode

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log

// Argument: listOf(Context, Boolean enabled)
val toggleAirplaneMode: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 2 && arg[0] is Context && arg[1] is Boolean) {
        val context = arg[0] as Context
        val enabled = arg[1] as Boolean

        try {
            // Requires root for API 17+
            val command = if (enabled) {
                "settings put global airplane_mode_on 1; am broadcast -a android.intent.action.AIRPLANE_MODE --ez state true"
            } else {
                "settings put global airplane_mode_on 0; am broadcast -a android.intent.action.AIRPLANE_MODE --ez state false"
            }
            Runtime.getRuntime().exec(arrayOf("su", "-c", command))
        } catch (e: Exception) {
            Log.e("AIRPLANE_MODE_ERROR", e.message ?: "Could not toggle airplane mode")
        }
    }
}