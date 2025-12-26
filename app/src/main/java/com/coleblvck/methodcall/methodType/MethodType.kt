package com.coleblvck.methodcall.methodType

import com.coleblvck.methodcall.methodType.definitions.launchActivityWithAction.launchActivityWithAction
import com.coleblvck.methodcall.methodType.definitions.openUrl.openUrl
import com.coleblvck.methodcall.methodType.definitions.packageLaunch.packageLaunch
import com.coleblvck.methodcall.methodType.definitions.playNotificationSound.playNotificationSound
import com.coleblvck.methodcall.methodType.definitions.reboot.reboot
import com.coleblvck.methodcall.methodType.definitions.sendSms.sendSms
import com.coleblvck.methodcall.methodType.definitions.setRingerMode.setRingerMode
import com.coleblvck.methodcall.methodType.definitions.setScreenBrightness.setScreenBrightness
import com.coleblvck.methodcall.methodType.definitions.setVolume.setVolume
import com.coleblvck.methodcall.methodType.definitions.shutdown.shutdown
import com.coleblvck.methodcall.methodType.definitions.startRecordingAudio.startRecordingAudio
import com.coleblvck.methodcall.methodType.definitions.takeScreenshot.takeScreenshot
import com.coleblvck.methodcall.methodType.definitions.toggleAirplaneMode.toggleAirplaneMode
import com.coleblvck.methodcall.methodType.definitions.toggleAutoRotate.toggleAutoRotate
import com.coleblvck.methodcall.methodType.definitions.toggleBluetooth.toggleBluetooth
import com.coleblvck.methodcall.methodType.definitions.toggleMobileData.toggleMobileData
import com.coleblvck.methodcall.methodType.definitions.toggleNfc.toggleNfc
import com.coleblvck.methodcall.methodType.definitions.toggleTorch.toggleTorch
import com.coleblvck.methodcall.methodType.definitions.toggleWifi.toggleWifi

enum class MethodType(private val method: (List<Any>) -> Unit) {
    PACKAGE_LAUNCH(packageLaunch),
    TOGGLE_TORCH(toggleTorch),
    SHUTDOWN(shutdown),
    REBOOT(reboot),
    SEND_SMS(sendSms),
    SET_RINGER_MODE(setRingerMode),
    TOGGLE_WIFI(toggleWifi),
    TOGGLE_BLUETOOTH(toggleBluetooth),
    SET_SCREEN_BRIGHTNESS(setScreenBrightness),
    TOGGLE_AIRPLANE_MODE(toggleAirplaneMode),
    OPEN_URL(openUrl),
    PLAY_NOTIFICATION_SOUND(playNotificationSound),
    TOGGLE_MOBILE_DATA(toggleMobileData),
    LAUNCH_ACTIVITY_WITH_ACTION(launchActivityWithAction),
    SET_VOLUME(setVolume),
    TAKE_SCREENSHOT(takeScreenshot),
    TOGGLE_AUTO_ROTATE(toggleAutoRotate),
    START_RECORDING_AUDIO(startRecordingAudio),
    TOGGLE_NFC(toggleNfc);

    fun execute(arg: List<Any>) {
        method(arg)
    }
}