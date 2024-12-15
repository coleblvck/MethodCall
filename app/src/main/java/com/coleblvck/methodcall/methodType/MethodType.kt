package com.coleblvck.methodcall.methodType

import com.coleblvck.methodcall.methodType.definitions.packageLaunch.packageLaunch
import com.coleblvck.methodcall.methodType.definitions.reboot.reboot
import com.coleblvck.methodcall.methodType.definitions.shutdown.shutdown
import com.coleblvck.methodcall.methodType.definitions.toggleTorch.toggleTorch

enum class MethodType(private val method: (List<Any>) -> Unit) {
    PACKAGE_LAUNCH(packageLaunch), TOGGLE_TORCH(toggleTorch), SHUTDOWN(shutdown), REBOOT(reboot);

    fun execute(arg: List<Any>) {
        method(arg)
    }
}