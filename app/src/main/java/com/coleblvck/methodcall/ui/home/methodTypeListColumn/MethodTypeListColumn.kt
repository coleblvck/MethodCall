package com.coleblvck.methodcall.ui.home.methodTypeListColumn

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.methodType.categories.superuserMethods
import com.coleblvck.methodcall.permissions.PermissionManager
import com.coleblvck.methodcall.ui.common.composables.ColumnWithHeading

@Composable
fun MethodTypeListColumn(
    chainToolBox: ChainToolBox,
    apps: State<List<App>>,
    superuserIsEnabled: State<Boolean>,
    permissionManager: PermissionManager? = null
) {
    ColumnWithHeading(
        modifier = Modifier
            .fillMaxSize(),
        headingText = "Actions",
        subHeadingText = "Select an action to start a new chain."
    ) {
        for (methodType in MethodType.entries) {
            if (methodType in listOf<MethodType>(
                    MethodType.START_RECORDING_AUDIO,
                    MethodType.TOGGLE_WIFI,
                    MethodType.TOGGLE_BLUETOOTH,
                )
            ) {
            } else {
                if (!superuserMethods.contains(methodType)) {
                    MethodTypeListItemCard(
                        chainToolBox = chainToolBox,
                        methodType = methodType,
                        apps = apps,
                        permissionManager = permissionManager
                    )
                } else {
                    if (superuserIsEnabled.value) {
                        MethodTypeListItemCard(
                            chainToolBox = chainToolBox,
                            methodType = methodType,
                            apps = apps,
                            permissionManager = permissionManager
                        )
                    }
                }
            }
        }
    }
}