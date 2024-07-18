package com.coleblvck.methodcall.data.appPackage.appState

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.coleblvck.methodcall.data.appPackage.App

class AppState(private val packageManager: PackageManager) {

    private val _apps: MutableState<List<App>> = mutableStateOf(listOf())
    val apps: State<List<App>> get() = _apps

    private fun getApps() {
        val resolveInfos = packageManager.queryIntentActivities(
            Intent(
                Intent.ACTION_MAIN,
                null
            ).addCategory(Intent.CATEGORY_LAUNCHER),
            0
        )
        val appList = mutableListOf<App>()
        for (resolveInfo: ResolveInfo in resolveInfos) {
            if (resolveInfo.activityInfo.packageName != "com.coleblvck.methodcall") {
                val app = App(
                    name = resolveInfo.loadLabel(packageManager).toString(),
                    packageName = resolveInfo.activityInfo.packageName,
                    icon = resolveInfo.activityInfo.loadIcon(packageManager)
                )
                appList.add(app)
            }
        }
        _apps.value = appList.sortedBy { it.name.lowercase() }
    }


    init {
        getApps()
    }
}