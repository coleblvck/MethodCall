package com.coleblvck.methodcall.state

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.coleblvck.methodcall.data.appPackage.appState.AppState
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.data.repositories.ChainRepository
import com.coleblvck.methodcall.methodType.MethodType
import kotlinx.coroutines.launch


class MethodCallViewModel(chainRepository: ChainRepository, packageManager: PackageManager): ViewModel() {
    val chainToolBox = ChainToolBox(chainRepository)
    val appState = AppState(packageManager)
    val liveChains = chainRepository.getChains()
    val getChainItemParameterName: (methodType: MethodType, parameter: String) -> String = {
        methodType: MethodType, parameter: String ->
        when {
            methodType == MethodType.PACKAGE_LAUNCH -> {
                val apps = appState.apps.value.filter { it.packageName == parameter }
                if (apps.isNotEmpty()) {
                    apps[0].name
                } else {
                    parameter
                }
            }
            else -> {
                parameter
            }
        }
    }
    init {
        viewModelScope.launch {

        }
    }

    companion object {
        val Factory: (ChainRepository, PackageManager) -> ViewModelProvider.Factory = {
            chainRepository: ChainRepository, packageManager: PackageManager ->
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return MethodCallViewModel(
                        chainRepository,
                        packageManager
                    ) as T
                }
            }
        }
    }
}