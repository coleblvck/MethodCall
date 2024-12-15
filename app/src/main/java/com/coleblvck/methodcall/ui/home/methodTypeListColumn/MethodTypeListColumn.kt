package com.coleblvck.methodcall.ui.home.methodTypeListColumn

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.methodType.categories.superuserMethods
import com.coleblvck.methodcall.ui.common.composables.ColumnWithHeading

@Composable
fun MethodTypeListColumn(
    chainToolBox: ChainToolBox,
    apps: State<List<App>>,
    superuserIsEnabled: State<Boolean>
) {
    ColumnWithHeading(
        modifier = Modifier.fillMaxSize(),
        headingText = "Actions",
        subHeadingText = "Select an action to start a new chain."
    ) {
        for (methodType in MethodType.entries) {
            if (!superuserMethods.contains(methodType)) {
                MethodTypeListItemCard(
                    chainToolBox = chainToolBox,
                    methodType = methodType,
                    apps = apps
                )
            } else {
                if (superuserIsEnabled.value) {
                    MethodTypeListItemCard(
                        chainToolBox = chainToolBox,
                        methodType = methodType,
                        apps = apps
                    )
                }
            }
        }
    }
}