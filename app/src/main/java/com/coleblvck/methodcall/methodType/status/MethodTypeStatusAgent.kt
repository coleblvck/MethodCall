package com.coleblvck.methodcall.methodType.status

import androidx.compose.runtime.State

class MethodTypeStatusAgent {
    companion object {
        val getTorchState: () -> State<Boolean> = {
            MethodTypeStatus.getInstance().torchStatus.state
        }
        val updateTorchState: (Boolean) -> Unit = {
            MethodTypeStatus.getInstance().apply {
                torchStatus.updateState(it)
            }
        }
    }
}