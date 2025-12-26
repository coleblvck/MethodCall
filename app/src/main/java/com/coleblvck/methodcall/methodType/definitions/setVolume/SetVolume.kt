package com.coleblvck.methodcall.methodType.definitions.setVolume

import android.content.Context
import android.media.AudioManager
import android.util.Log

// Argument: listOf(Context, String streamType, Int volume)
// streamType: "RING", "MEDIA", "ALARM", "NOTIFICATION"
val setVolume: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 3 && arg[0] is Context && arg[1] is String && arg[2] is Int) {
        val context = arg[0] as Context
        val streamType = arg[1] as String
        val volume = arg[2] as Int
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        try {
            val stream = when (streamType) {
                "RING" -> AudioManager.STREAM_RING
                "MEDIA" -> AudioManager.STREAM_MUSIC
                "ALARM" -> AudioManager.STREAM_ALARM
                "NOTIFICATION" -> AudioManager.STREAM_NOTIFICATION
                else -> AudioManager.STREAM_RING
            }

            val maxVolume = audioManager.getStreamMaxVolume(stream)
            val targetVolume = volume.coerceIn(0, maxVolume)
            audioManager.setStreamVolume(stream, targetVolume, 0)
        } catch (e: Exception) {
            Log.e("SET_VOLUME_ERROR", e.message ?: "Could not set volume")
        }
    }
}