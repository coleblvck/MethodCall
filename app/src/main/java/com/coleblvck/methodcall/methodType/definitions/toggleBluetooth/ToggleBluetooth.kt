package com.coleblvck.methodcall.methodType.definitions.toggleBluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat

// Argument: listOf(Context, Boolean enabled)
val toggleBluetooth: (arg: List<Any>) -> Unit = { arg: List<Any> ->
    if (arg.size == 2 && arg[0] is Context && arg[1] is Boolean) {
        val context = arg[0] as Context
        val enabled = arg[1] as Boolean

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // API 33+ requires BLUETOOTH_CONNECT permission
                if (hasBluetoothConnectPermission(context)) {
                    toggleBluetoothModern(enabled)
                } else {
                    Log.e("TOGGLE_BLUETOOTH_ERROR", "BLUETOOTH_CONNECT permission not granted")
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // API 31-32 requires BLUETOOTH_CONNECT permission
                if (hasBluetoothConnectPermission(context)) {
                    toggleBluetoothModern(enabled)
                } else {
                    Log.e("TOGGLE_BLUETOOTH_ERROR", "BLUETOOTH_CONNECT permission not granted")
                }
            } else {
                // API 30 and below uses legacy Bluetooth permissions
                if (hasLegacyBluetoothPermission(context)) {
                    toggleBluetoothLegacy(enabled)
                } else {
                    Log.e("TOGGLE_BLUETOOTH_ERROR", "BLUETOOTH_ADMIN permission not granted")
                }
            }
        } catch (e: SecurityException) {
            Log.e("TOGGLE_BLUETOOTH_ERROR", "SecurityException: ${e.message}")
        } catch (e: Exception) {
            Log.e("TOGGLE_BLUETOOTH_ERROR", e.message ?: "Could not toggle Bluetooth")
        }
    }
}

@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
private fun toggleBluetoothModern(enabled: Boolean) {
    try {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Log.e("TOGGLE_BLUETOOTH_ERROR", "Bluetooth adapter not available")
            return
        }

        if (enabled) {
            bluetoothAdapter.enable()
        } else {
            bluetoothAdapter.disable()
        }
    } catch (e: SecurityException) {
        Log.e("TOGGLE_BLUETOOTH_ERROR", "SecurityException in modern toggle: ${e.message}")
    }
}

@Suppress("DEPRECATION")
@RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
private fun toggleBluetoothLegacy(enabled: Boolean) {
    try {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Log.e("TOGGLE_BLUETOOTH_ERROR", "Bluetooth adapter not available")
            return
        }

        if (enabled) {
            bluetoothAdapter.enable()
        } else {
            bluetoothAdapter.disable()
        }
    } catch (e: SecurityException) {
        Log.e("TOGGLE_BLUETOOTH_ERROR", "SecurityException in legacy toggle: ${e.message}")
    }
}

private fun hasBluetoothConnectPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true // Not required for older versions
    }
}

private fun hasLegacyBluetoothPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.BLUETOOTH_ADMIN
    ) == PackageManager.PERMISSION_GRANTED
}