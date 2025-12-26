package com.coleblvck.methodcall.methodType.definitions.takeScreenshot

import android.os.Environment
import android.util.Log

// Argument: listOf()
val takeScreenshot: (arg: List<Any>) -> Unit = {
    try {
        val timestamp = System.currentTimeMillis()
        val screenshotPath = "${Environment.getExternalStorageDirectory()}/Pictures/Screenshots/screenshot_$timestamp.png"
        val command = "screencap -p $screenshotPath"
        Runtime.getRuntime().exec(arrayOf("su", "-c", command))
    } catch (e: Exception) {
        Log.e("SCREENSHOT_ERROR", e.message ?: "Could not take screenshot")
    }
}