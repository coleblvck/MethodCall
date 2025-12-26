package com.coleblvck.methodcall.methodType.definitions.toggleNfc

import android.util.Log

// Argument: listOf(Boolean enabled)
val toggleNfc: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 1 && arg[0] is Boolean) {
        val enabled = arg[0] as Boolean

        try {
            val command = if (enabled) {
                "svc nfc enable"
            } else {
                "svc nfc disable"
            }
            Runtime.getRuntime().exec(arrayOf("su", "-c", command))
        } catch (e: Exception) {
            Log.e("TOGGLE_NFC_ERROR", e.message ?: "Could not toggle NFC")
        }
    }
}