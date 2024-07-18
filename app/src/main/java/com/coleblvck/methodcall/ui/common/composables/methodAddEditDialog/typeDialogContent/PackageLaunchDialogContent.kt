package com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.ui.common.composables.AppCard
import com.coleblvck.methodcall.ui.common.composables.AppRow

@Composable
fun PackageLaunchDialogContent(
    dismissCallback: () -> Unit,
    chainToolBox: ChainToolBox,
    apps: State<List<App>>,
    phoneNumber: State<String>,
    params: List<String>? = null,
    index: Int? = null,
) {
    val selectedApp: MutableState<App?> = remember {
        mutableStateOf(null)
    }
    val selectAppCallback: (App) -> Unit = {
        selectedApp.value = it
    }

    fun saveConfig(phone: String, app: App) {
        val args = mutableListOf(app.packageName)
        chainToolBox.createOrReplaceChainItem(
            phoneNumber = phone,
            method = MethodType.PACKAGE_LAUNCH,
            params = args
        )
    }
    when {
        selectedApp.value != null -> ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Selected:",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    ElevatedCard(
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        onClick = {
                            saveConfig(phoneNumber.value, selectedApp.value!!)
                            dismissCallback()
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Save",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
                AppCard(
                    app = selectedApp.value!!,
                    clickCallback = { selectedApp.value = null },
                    iconOnly = true,
                    colors = CardDefaults.cardColors(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
    AppRow(
        apps = apps.value,
        clickCallback = selectAppCallback
    )
}