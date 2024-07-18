package com.coleblvck.methodcall.ui.home.methodTypeListColumn

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.ui.common.composables.ColumnWithHeading

@Composable
fun MethodTypeListColumn(
    chainToolBox: ChainToolBox,
    apps: State<List<App>>,
) {
    ColumnWithHeading(
        modifier = Modifier.fillMaxSize(),
        headingText = "Actions",
        subHeadingText = "Select an action to start a new chain."
    ) {
        for (methodType in MethodType.entries) {
            MethodTypeListItemCard(
                chainToolBox = chainToolBox,
                methodType = methodType,
                apps = apps
            )
        }
    }
}