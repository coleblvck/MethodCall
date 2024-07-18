package com.coleblvck.methodcall.methodType

import com.coleblvck.methodcall.methodType.definitions.packageLaunch.packageLaunch
import com.coleblvck.methodcall.methodType.definitions.toggleTorch.toggleTorch

enum class MethodType(private val method: (List<Any>) -> Unit) {
    PACKAGE_LAUNCH(packageLaunch), TOGGLE_TORCH(toggleTorch);

    fun execute(arg: List<Any>) {
        method(arg)
    }
}