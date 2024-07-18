package com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.methodType.helpers.getLegibleMethodTypeName
import com.coleblvck.methodcall.ui.common.composables.ColumnWithHeading
import com.coleblvck.methodcall.ui.common.composables.TextEditCard
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent.PackageLaunchDialogContent
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent.ToggleTorchDialogContent

@Composable
fun MethodAddEditDialog(
    dismissCallback: () -> Unit,
    chainToolBox: ChainToolBox,
    methodType: MethodType,
    apps: State<List<App>>,
    phoneNumber: String? = null,
    params: List<String>? = null,
    index: Int? = null
) {
    Dialog(onDismissRequest = dismissCallback) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            )
        ) {

            val phoneNumberDisplay = remember {
                mutableStateOf("")
            }
            val phoneNumberEdit: (String) -> Unit = {
                phoneNumberDisplay.value = it
            }
            ColumnWithHeading(
                topSpace = 40.dp,
                headingText = getLegibleMethodTypeName(methodType),
                subHeadingText = "Enter a Phone Number to start or continue a chain with this action." +
                        " Leave the space blank for a chain that applies to all Phone Numbers.",
                subHeadingTextSize = 18.sp
            ) {
                if (phoneNumber != null) {
                    phoneNumberDisplay.value = phoneNumber
                }

                TextEditCard(
                    icon = Icons.Default.Phone,
                    displayText = phoneNumberDisplay,
                    onEditCallback = phoneNumberEdit
                )

                when (methodType) {
                    MethodType.PACKAGE_LAUNCH -> PackageLaunchDialogContent(
                        dismissCallback = dismissCallback,
                        chainToolBox = chainToolBox,
                        apps = apps,
                        phoneNumber = phoneNumberDisplay,
                        params = params,
                        index = index
                    )

                    MethodType.TOGGLE_TORCH -> ToggleTorchDialogContent(
                        dismissCallback = dismissCallback,
                        chainToolBox = chainToolBox,
                        phoneNumber = phoneNumberDisplay,
                        params = params,
                        index = index
                    )
                }
            }
        }
    }
}