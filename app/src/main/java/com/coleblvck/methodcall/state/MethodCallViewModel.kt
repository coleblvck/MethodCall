package com.coleblvck.methodcall.state

import android.content.pm.PackageManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.coleblvck.methodcall.data.appPackage.appState.AppState
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.data.repositories.ChainRepository
import com.coleblvck.methodcall.data.userPreferences.UserPreferencesToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.permissions.PermissionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MethodCallViewModel(chainRepository: ChainRepository, packageManager: PackageManager, store: DataStore<Preferences>) :
    ViewModel() {


    val chainToolBox = ChainToolBox(chainRepository)
    val appState = AppState(packageManager)
    val liveChains = chainRepository.getChains()

    private val _permissionManager = MutableStateFlow<PermissionManager?>(null)
    val permissionManager: StateFlow<PermissionManager?> = _permissionManager.asStateFlow()

    fun setPermissionManager(manager: PermissionManager) {
        _permissionManager.value = manager
    }


    val getChainItemParameterName: (methodType: MethodType, parameter: String) -> String =
        { methodType: MethodType, parameter: String ->
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

    val userPreferencesToolBox = UserPreferencesToolBox(store)

    val superUserTools: SuperUserTools = SuperUserTools()

    inner class SuperUserTools() {
        private fun isDeviceRooted(): Boolean {
            val buildTags = android.os.Build.TAGS
            return buildTags != null && buildTags.contains("test-keys")
        }

        private fun canAccessSuperuser(): Boolean {
            return try {
                val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "id"))
                process.waitFor() == 0
            } catch (e: Exception) {
                false
            }
        }

        val toggleSuperUserPrivileges: (state: Boolean) -> Unit = { state: Boolean ->
            if (state) {
                if (canAccessSuperuser()) {
                    userPreferencesToolBox.updateSuperuserEnabledStatus(state)
                }
            } else {
                userPreferencesToolBox.updateSuperuserEnabledStatus(state)
            }
        }
    }

    companion object {
        val Factory: (ChainRepository, PackageManager, DataStore<Preferences>,) -> ViewModelProvider.Factory =
            { chainRepository: ChainRepository, packageManager: PackageManager, store: DataStore<Preferences>,->
                object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>,
                        extras: CreationExtras
                    ): T {
                        return MethodCallViewModel(
                            chainRepository,
                            packageManager,
                            store
                        ) as T
                    }
                }
            }
    }
}