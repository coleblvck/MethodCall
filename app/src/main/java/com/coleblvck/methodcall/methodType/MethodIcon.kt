package com.coleblvck.methodcall.methodType

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

val methodIcon: (MethodType) -> ImageVector = { methodType ->
    when (methodType) {
        MethodType.PACKAGE_LAUNCH -> Icons.Default.PhoneAndroid
        MethodType.REBOOT -> Icons.Default.RestartAlt
        MethodType.SHUTDOWN -> Icons.Default.PowerSettingsNew
        MethodType.TOGGLE_TORCH -> Icons.Default.FlashlightOn
        MethodType.SEND_SMS -> Icons.Default.Sms
        MethodType.SET_RINGER_MODE -> Icons.Default.Phone
        MethodType.TOGGLE_WIFI -> Icons.Default.Wifi
        MethodType.TOGGLE_BLUETOOTH -> Icons.Default.Bluetooth
        MethodType.SET_SCREEN_BRIGHTNESS -> Icons.Default.Brightness6
        MethodType.TOGGLE_AIRPLANE_MODE -> Icons.Default.Flight
        MethodType.OPEN_URL -> Icons.Default.Language
        MethodType.PLAY_NOTIFICATION_SOUND -> Icons.Default.Notifications
        MethodType.TOGGLE_MOBILE_DATA -> Icons.Default.SignalCellularAlt
        MethodType.LAUNCH_ACTIVITY_WITH_ACTION -> Icons.Default.PlayArrow
        MethodType.SET_VOLUME -> Icons.Default.VolumeUp
        MethodType.TAKE_SCREENSHOT -> Icons.Default.CameraAlt
        MethodType.TOGGLE_AUTO_ROTATE -> Icons.Default.ScreenRotation
        MethodType.START_RECORDING_AUDIO -> Icons.Default.Mic
        MethodType.TOGGLE_NFC -> Icons.Default.Nfc
    }
}