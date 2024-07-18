package com.coleblvck.methodcall.methodType.definitions.packageLaunch

import android.content.Context
import android.util.Log


// Argument: listOf(Context, String)
val packageLaunch: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 2 && arg[0] is Context && arg[1] is String){
        val context: Context = arg[0] as Context
        val packageName: String = arg[1] as String
        val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            try {
                context.startActivity(launchIntent)
            } catch (e: Exception) {
                Log.e("APP_LAUNCH_ERROR", e.message ?: "Could not launch application")
            }
        }
    }
}