package com.coleblvck.methodcall.methodType.definitions.toggleMobileData

import android.util.Log

// Argument: listOf(Boolean enabled)
val toggleMobileData: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 1 && arg[0] is Boolean) {
        val enabled = arg[0] as Boolean

        try {
            val command = if (enabled) {
                "svc data enable"
            } else {
                "svc data disable"
            }
            Runtime.getRuntime().exec(arrayOf("su", "-c", command))
        } catch (e: Exception) {
            Log.e("TOGGLE_DATA_ERROR", e.message ?: "Could not toggle mobile data")
        }
    }
}