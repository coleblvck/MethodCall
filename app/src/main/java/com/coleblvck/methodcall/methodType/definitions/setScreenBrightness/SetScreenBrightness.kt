package com.coleblvck.methodcall.methodType.definitions.setScreenBrightness

import android.content.Context
import android.provider.Settings
import android.util.Log

// Argument: listOf(Context, Int brightness) where brightness = 0-255
val setScreenBrightness: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 2 && arg[0] is Context && arg[1] is Int) {
        val context = arg[0] as Context
        val brightness = (arg[1] as Int).coerceIn(0, 255)

        try {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                brightness
            )
        } catch (e: Exception) {
            Log.e("SET_BRIGHTNESS_ERROR", e.message ?: "Could not set brightness")
        }
    }
}