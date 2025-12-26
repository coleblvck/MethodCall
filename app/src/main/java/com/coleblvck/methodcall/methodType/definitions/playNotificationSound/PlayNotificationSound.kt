package com.coleblvck.methodcall.methodType.definitions.playNotificationSound

import android.content.Context
import android.media.RingtoneManager
import android.util.Log

// Argument: listOf(Context)
val playNotificationSound: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 1 && arg[0] is Context) {
        val context = arg[0] as Context

        try {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(context, notification)
            ringtone.play()
        } catch (e: Exception) {
            Log.e("PLAY_SOUND_ERROR", e.message ?: "Could not play notification sound")
        }
    }
}