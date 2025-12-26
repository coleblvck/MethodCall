package com.coleblvck.methodcall.methodType.definitions.setRingerMode

import android.content.Context
import android.media.AudioManager
import android.util.Log

// Argument: listOf(Context, String mode) where mode = "SILENT", "VIBRATE", "NORMAL"
val setRingerMode: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 2 && arg[0] is Context && arg[1] is String) {
        val context = arg[0] as Context
        val mode = arg[1] as String
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        try {
            val ringerMode = when (mode) {
                "SILENT" -> AudioManager.RINGER_MODE_SILENT
                "VIBRATE" -> AudioManager.RINGER_MODE_VIBRATE
                "NORMAL" -> AudioManager.RINGER_MODE_NORMAL
                else -> AudioManager.RINGER_MODE_NORMAL
            }
            audioManager.ringerMode = ringerMode
        } catch (e: Exception) {
            Log.e("SET_RINGER_MODE_ERROR", e.message ?: "Could not set ringer mode")
        }
    }
}