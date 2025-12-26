package com.coleblvck.methodcall.methodType.definitions.toggleAutoRotate

import android.content.Context
import android.provider.Settings
import android.util.Log

// Argument: listOf(Context, Boolean enabled)
val toggleAutoRotate: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 2 && arg[0] is Context && arg[1] is Boolean) {
        val context = arg[0] as Context
        val enabled = arg[1] as Boolean

        try {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.ACCELEROMETER_ROTATION,
                if (enabled) 1 else 0
            )
        } catch (e: Exception) {
            Log.e("AUTO_ROTATE_ERROR", e.message ?: "Could not toggle auto rotate")
        }
    }
}