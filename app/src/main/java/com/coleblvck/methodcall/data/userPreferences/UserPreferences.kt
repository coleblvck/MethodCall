package com.coleblvck.methodcall.data.userPreferences

import androidx.compose.runtime.MutableState

data class UserPreferences (
    val superuserEnabled: MutableState<Boolean>,
)