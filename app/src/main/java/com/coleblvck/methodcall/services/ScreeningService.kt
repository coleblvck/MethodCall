package com.coleblvck.methodcall.services

import android.content.Intent
import android.os.Build
import android.telecom.Call.Details
import android.telecom.CallScreeningService
import androidx.annotation.RequiresApi
import com.coleblvck.methodcall.services.runner.RunnerService

class ScreeningService : CallScreeningService() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onScreenCall(callDetails: Details) {
        val isIncoming = callDetails.callDirection == Details.DIRECTION_INCOMING

        if (isIncoming) {
            val phoneNumber = getPhoneNumber(callDetails)
            if (phoneNumber != null) {
                val intent = Intent(this@ScreeningService, RunnerService::class.java)
                intent.putExtra("EXTRA_PHONE_NUMBER", phoneNumber)
                this.startService(intent)
            }
        }
    }

    private fun getPhoneNumber(callDetails: Details): String? {
        val handle = callDetails.handle
        if (handle != null) {
            return handle.schemeSpecificPart
        } else {
            val gatewayInfo = callDetails.gatewayInfo
            if (gatewayInfo != null) {
                return gatewayInfo.originalAddress.schemeSpecificPart
            }
        }
        return null
    }
}
