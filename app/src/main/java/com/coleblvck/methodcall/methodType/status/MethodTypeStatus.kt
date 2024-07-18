package com.coleblvck.methodcall.methodType.status

import com.coleblvck.methodcall.methodType.definitions.toggleTorch.TorchStatus

class MethodTypeStatus private constructor() {
    companion object {
        @Volatile private var instance: MethodTypeStatus? = null

        fun getInstance() : MethodTypeStatus =
            instance ?: synchronized(this) {
                instance ?: MethodTypeStatus().also { instance = it }
            }
    }
    var torchStatus: TorchStatus = TorchStatus()
}