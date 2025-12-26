package com.coleblvck.methodcall.methodType.definitions.sendSms

import android.content.Context
import android.telephony.SmsManager
import android.util.Log

// Argument: listOf(Context, String phoneNumber, String message)
val sendSms: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 3 && arg[0] is Context && arg[1] is String && arg[2] is String) {
        val context = arg[0] as Context
        val phoneNumber = arg[1] as String
        val message = arg[2] as String

        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        } catch (e: Exception) {
            Log.e("SEND_SMS_ERROR", e.message ?: "Could not send SMS")
        }
    }
}