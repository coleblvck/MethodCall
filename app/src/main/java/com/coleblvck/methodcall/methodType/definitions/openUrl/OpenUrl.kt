package com.coleblvck.methodcall.methodType.definitions.openUrl

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

// Argument: listOf(Context, String url)
val openUrl: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 2 && arg[0] is Context && arg[1] is String) {
        val context = arg[0] as Context
        val url = arg[1] as String

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("OPEN_URL_ERROR", e.message ?: "Could not open URL")
        }
    }
}