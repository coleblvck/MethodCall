package com.coleblvck.methodcall.methodType.definitions.toggleTorch

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class TorchStatus {
    private val _state: MutableState<Boolean> = mutableStateOf(false)
    val state: State<Boolean> get() = _state

    val updateState: (Boolean) -> Unit = {
        _state.value = it
    }
}