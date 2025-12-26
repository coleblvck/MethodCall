package com.coleblvck.methodcall.methodType.definitions.launchActivityWithAction

import android.content.Context
import android.content.Intent
import android.util.Log

// Argument: listOf(Context, String action) e.g., "android.settings.SETTINGS"
val launchActivityWithAction: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 2 && arg[0] is Context && arg[1] is String) {
        val context = arg[0] as Context
        val action = arg[1] as String

        try {
            val intent = Intent(action)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("LAUNCH_ACTIVITY_ERROR", e.message ?: "Could not launch activity")
        }
    }
}