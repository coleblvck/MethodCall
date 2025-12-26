package com.coleblvck.methodcall.permissions

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.coleblvck.methodcall.methodType.MethodType

class PermissionManager(private val activity: ComponentActivity) {

    private var permissionLauncher: ActivityResultLauncher<Array<String>>? = null
    private var writeSettingsLauncher: ActivityResultLauncher<Intent>? = null
    private var notificationPolicyLauncher: ActivityResultLauncher<Intent>? = null
    private var onPermissionResult: ((Boolean) -> Unit)? = null

    init {
        setupLaunchers()
    }

    private fun setupLaunchers() {
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val allGranted = permissions.values.all { it }
            onPermissionResult?.invoke(allGranted)
        }

        writeSettingsLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val granted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Settings.System.canWrite(activity)
            } else {
                true
            }
            onPermissionResult?.invoke(granted)
        }

        notificationPolicyLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val granted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val notificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE)
                        as android.app.NotificationManager
                notificationManager.isNotificationPolicyAccessGranted
            } else {
                true
            }
            onPermissionResult?.invoke(granted)
        }
    }

    fun checkAndRequestPermission(
        methodType: MethodType,
        onResult: (Boolean) -> Unit
    ) {
        onPermissionResult = onResult
        val requiredPermissions = getRequiredPermissions(methodType)

        when {
            requiredPermissions.isEmpty() -> {
                onResult(true)
            }
            requiredPermissions.contains(WRITE_SETTINGS) -> {
                if (hasPermission(WRITE_SETTINGS)) {
                    onResult(true)
                } else {
                    requestWriteSettings()
                }
            }
            requiredPermissions.contains(NOTIFICATION_POLICY) -> {
                if (hasPermission(NOTIFICATION_POLICY)) {
                    onResult(true)
                } else {
                    requestNotificationPolicy()
                }
            }
            else -> {
                val missing = requiredPermissions.filter { !hasPermission(it) }
                if (missing.isEmpty()) {
                    onResult(true)
                } else {
                    permissionLauncher?.launch(missing.toTypedArray())
                }
            }
        }
    }

    fun hasAllPermissions(methodType: MethodType): Boolean {
        val required = getRequiredPermissions(methodType)
        return required.all { hasPermission(it) }
    }

    private fun hasPermission(permission: String): Boolean {
        return when (permission) {
            WRITE_SETTINGS -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Settings.System.canWrite(activity)
                } else {
                    true
                }
            }
            NOTIFICATION_POLICY -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val notificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE)
                            as android.app.NotificationManager
                    notificationManager.isNotificationPolicyAccessGranted
                } else {
                    true
                }
            }
            else -> {
                ContextCompat.checkSelfPermission(activity, permission) ==
                        PackageManager.PERMISSION_GRANTED
            }
        }
    }

    private fun requestWriteSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                data = Uri.parse("package:${activity.packageName}")
            }
            writeSettingsLauncher?.launch(intent)
        }
    }

    private fun requestNotificationPolicy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            notificationPolicyLauncher?.launch(intent)
        }
    }

    private fun getRequiredPermissions(methodType: MethodType): List<String> {
        return when (methodType) {
            MethodType.SEND_SMS -> listOf(Manifest.permission.SEND_SMS)

            MethodType.SET_RINGER_MODE -> listOf(NOTIFICATION_POLICY)

            MethodType.TOGGLE_WIFI -> listOf(
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
            )

            MethodType.TOGGLE_BLUETOOTH -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    listOf(Manifest.permission.BLUETOOTH_CONNECT)
                } else {
                    listOf(Manifest.permission.BLUETOOTH_ADMIN)
                }
            }

            MethodType.SET_SCREEN_BRIGHTNESS, MethodType.TOGGLE_AUTO_ROTATE ->
                listOf(WRITE_SETTINGS)

            MethodType.SET_VOLUME -> listOf(NOTIFICATION_POLICY)

            MethodType.START_RECORDING_AUDIO -> {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    listOf(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                } else {
                    listOf(Manifest.permission.RECORD_AUDIO)
                }
            }

            // Methods requiring root or no permissions
            MethodType.PACKAGE_LAUNCH, MethodType.TOGGLE_TORCH,
            MethodType.SHUTDOWN, MethodType.REBOOT,
            MethodType.TOGGLE_AIRPLANE_MODE, MethodType.OPEN_URL,
            MethodType.PLAY_NOTIFICATION_SOUND, MethodType.TOGGLE_MOBILE_DATA,
            MethodType.LAUNCH_ACTIVITY_WITH_ACTION, MethodType.TAKE_SCREENSHOT,
            MethodType.TOGGLE_NFC -> emptyList()
        }
    }

    companion object {
        private const val WRITE_SETTINGS = "android.permission.WRITE_SETTINGS"
        private const val NOTIFICATION_POLICY = "android.permission.ACCESS_NOTIFICATION_POLICY"
    }
}