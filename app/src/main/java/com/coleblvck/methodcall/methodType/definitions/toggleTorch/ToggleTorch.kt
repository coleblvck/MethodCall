package com.coleblvck.methodcall.methodType.definitions.toggleTorch

import android.content.Context
import android.hardware.camera2.CameraManager
import android.util.Log
import com.coleblvck.methodcall.methodType.status.MethodTypeStatusAgent


// Argument: listOf(Context)
val toggleTorch: (arg: List<Any>) -> Unit = {
    arg: List<Any> ->
    if (arg.size == 1 && arg[0] is Context) {
        val context = arg[0] as Context
        val value = MethodTypeStatusAgent.getTorchState().value
        turnFlashOnOff(context, !value)
    }
}

fun turnFlashOnOff(context: Context, value: Boolean) {
    MethodTypeStatusAgent.updateTorchState(value)
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraId = cameraManager.cameraIdList[0]
    try {
        cameraManager.setTorchMode(cameraId, value)
    } catch (e: Exception) {
        Log.e("TORCH_TOGGLE_ERROR", e.message?: "Could not toggle torch")
    }
}
