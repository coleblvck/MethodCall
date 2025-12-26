package com.coleblvck.methodcall.services.runner

import android.content.Context
import com.coleblvck.methodcall.data.chain.Chain
import com.coleblvck.methodcall.methodType.MethodType

class RunnerHandler {
    companion object {
        fun handleExecution(
            context: Context,
            phoneNumber: String,
            chains: List<Chain>
        ) {
            val filteredChainList = chains.filter { it.phoneNumber == phoneNumber || it.phoneNumber == "" }
            if (filteredChainList.isNotEmpty()) {
                for (chain in filteredChainList) {
                    organizeExecution(context, chain.methodChain)
                }
            }
        }

        private fun organizeExecution(
            context: Context,
            methods: List<Pair<MethodType, List<String>>>
        ) {
            for (method in methods) {
                execute(context, method.first, method.second)
            }
        }

        private fun execute(context: Context, methodType: MethodType, args: List<String>) {
            when (methodType) {
                MethodType.PACKAGE_LAUNCH -> {
                    if (args.isNotEmpty()) {
                        args.forEach { packageName: String ->
                            MethodType.PACKAGE_LAUNCH.execute(listOf(context, packageName))
                        }
                    }
                }

                MethodType.SEND_SMS -> {
                    if (args.size >= 2) {
                        val phoneNumber = args[0]
                        val message = args[1]
                        MethodType.SEND_SMS.execute(listOf(context, phoneNumber, message))
                    }
                }

                MethodType.SET_RINGER_MODE -> {
                    if (args.isNotEmpty()) {
                        val mode = args[0] // "SILENT", "VIBRATE", "NORMAL"
                        MethodType.SET_RINGER_MODE.execute(listOf(context, mode))
                    }
                }

                MethodType.TOGGLE_WIFI -> {
                    if (args.isNotEmpty()) {
                        val enabled = args[0].toBoolean()
                        MethodType.TOGGLE_WIFI.execute(listOf(context, enabled))
                    }
                }

                MethodType.TOGGLE_BLUETOOTH -> {
                    if (args.isNotEmpty()) {
                        val enabled = args[0].toBoolean()
                        MethodType.TOGGLE_BLUETOOTH.execute(listOf(context, enabled))
                    }
                }

                MethodType.SET_SCREEN_BRIGHTNESS -> {
                    if (args.isNotEmpty()) {
                        val brightness = args[0].toIntOrNull() ?: 128
                        MethodType.SET_SCREEN_BRIGHTNESS.execute(listOf(context, brightness))
                    }
                }

                MethodType.TOGGLE_AIRPLANE_MODE -> {
                    if (args.isNotEmpty()) {
                        val enabled = args[0].toBoolean()
                        MethodType.TOGGLE_AIRPLANE_MODE.execute(listOf(context, enabled))
                    }
                }

                MethodType.OPEN_URL -> {
                    if (args.isNotEmpty()) {
                        val url = args[0]
                        MethodType.OPEN_URL.execute(listOf(context, url))
                    }
                }

                MethodType.PLAY_NOTIFICATION_SOUND -> {
                    MethodType.PLAY_NOTIFICATION_SOUND.execute(listOf(context))
                }

                MethodType.TOGGLE_MOBILE_DATA -> {
                    if (args.isNotEmpty()) {
                        val enabled = args[0].toBoolean()
                        MethodType.TOGGLE_MOBILE_DATA.execute(listOf(enabled))
                    }
                }

                MethodType.LAUNCH_ACTIVITY_WITH_ACTION -> {
                    if (args.isNotEmpty()) {
                        val action = args[0]
                        MethodType.LAUNCH_ACTIVITY_WITH_ACTION.execute(listOf(context, action))
                    }
                }

                MethodType.SET_VOLUME -> {
                    if (args.size >= 2) {
                        val streamType = args[0] // "RING", "MEDIA", "ALARM", "NOTIFICATION"
                        val volume = args[1].toIntOrNull() ?: 50
                        MethodType.SET_VOLUME.execute(listOf(context, streamType, volume))
                    }
                }

                MethodType.TAKE_SCREENSHOT -> {
                    MethodType.TAKE_SCREENSHOT.execute(listOf())
                }

                MethodType.TOGGLE_AUTO_ROTATE -> {
                    if (args.isNotEmpty()) {
                        val enabled = args[0].toBoolean()
                        MethodType.TOGGLE_AUTO_ROTATE.execute(listOf(context, enabled))
                    }
                }

                MethodType.START_RECORDING_AUDIO -> {
                    if (args.isNotEmpty()) {
                        val filename = args[0]
                        MethodType.START_RECORDING_AUDIO.execute(listOf(context, filename))
                    }
                }

                MethodType.TOGGLE_NFC -> {
                    if (args.isNotEmpty()) {
                        val enabled = args[0].toBoolean()
                        MethodType.TOGGLE_NFC.execute(listOf(enabled))
                    }
                }

                MethodType.TOGGLE_TORCH -> {
                    MethodType.TOGGLE_TORCH.execute(listOf(context))
                }

                MethodType.SHUTDOWN -> {
                    MethodType.SHUTDOWN.execute(listOf())
                }

                MethodType.REBOOT -> {
                    MethodType.REBOOT.execute(listOf())
                }
            }
        }
    }
}