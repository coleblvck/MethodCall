package com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.methodType.helpers.getLegibleMethodTypeName
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent.BrightnessDialogContent
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent.GeneralDialogContent
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent.LaunchActivityDialogContent
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent.OpenUrlDialogContent
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent.PackageLaunchDialogContent
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent.RingerModeDialogContent
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent.SendSmsDialogContent
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent.ToggleDialogContent
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent.VolumeDialogContent

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
    Dialog(
        onDismissRequest = dismissCallback,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(vertical = 32.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            val phoneNumberDisplay = remember { mutableStateOf("") }
            val phoneNumberEdit: (String) -> Unit = { phoneNumberDisplay.value = it }

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                // Header
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = getLegibleMethodTypeName(methodType),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Configure action parameters",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                // Phone number section
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Phone Number",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "Leave blank to apply to all numbers",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )

                    if (phoneNumber != null) {
                        phoneNumberDisplay.value = phoneNumber
                    }

                    PremiumTextEditCard(
                        icon = Icons.Default.Phone,
                        displayText = phoneNumberDisplay,
                        onEditCallback = phoneNumberEdit
                    )
                }

                // Method-specific content
                when (methodType) {
                    MethodType.PACKAGE_LAUNCH -> PackageLaunchDialogContent(
                        dismissCallback = dismissCallback,
                        chainToolBox = chainToolBox,
                        apps = apps,
                        phoneNumber = phoneNumberDisplay,
                        params = params,
                        index = index,
                    )


                    MethodType.SEND_SMS -> SendSmsDialogContent(
                        dismissCallback = dismissCallback,
                        chainToolBox = chainToolBox,
                        phoneNumber = phoneNumberDisplay,
                        params = params,
                        index = index,
                    )

                    MethodType.SET_RINGER_MODE -> RingerModeDialogContent(
                        dismissCallback = dismissCallback,
                        chainToolBox = chainToolBox,
                        phoneNumber = phoneNumberDisplay,
                        params = params,
                        index = index,
                    )

                    MethodType.TOGGLE_WIFI, MethodType.TOGGLE_BLUETOOTH,
                    MethodType.TOGGLE_MOBILE_DATA, MethodType.TOGGLE_AIRPLANE_MODE,
                    MethodType.TOGGLE_AUTO_ROTATE, MethodType.TOGGLE_NFC -> ToggleDialogContent(
                        dismissCallback = dismissCallback,
                        chainToolBox = chainToolBox,
                        phoneNumber = phoneNumberDisplay,
                        methodType = methodType,
                        params = params,
                        index = index,
                    )

                    MethodType.SET_SCREEN_BRIGHTNESS -> BrightnessDialogContent(
                        dismissCallback = dismissCallback,
                        chainToolBox = chainToolBox,
                        phoneNumber = phoneNumberDisplay,
                        params = params,
                        index = index,
                    )

                    MethodType.OPEN_URL -> OpenUrlDialogContent(
                        dismissCallback = dismissCallback,
                        chainToolBox = chainToolBox,
                        phoneNumber = phoneNumberDisplay,
                        params = params,
                        index = index,
                    )

                    MethodType.SET_VOLUME -> VolumeDialogContent(
                        dismissCallback = dismissCallback,
                        chainToolBox = chainToolBox,
                        phoneNumber = phoneNumberDisplay,
                        params = params,
                        index = index,
                    )

                    MethodType.LAUNCH_ACTIVITY_WITH_ACTION -> LaunchActivityDialogContent(
                        dismissCallback = dismissCallback,
                        chainToolBox = chainToolBox,
                        phoneNumber = phoneNumberDisplay,
                        params = params,
                        index = index,
                    )

                    // Simple methods with no parameters
                    MethodType.TOGGLE_TORCH, MethodType.SHUTDOWN, MethodType.REBOOT,
                    MethodType.PLAY_NOTIFICATION_SOUND, MethodType.TAKE_SCREENSHOT,
                    MethodType.START_RECORDING_AUDIO ->
                        GeneralDialogContent(
                            dismissCallback = dismissCallback,
                            chainToolBox = chainToolBox,
                            phoneNumber = phoneNumberDisplay,
                            methodType = methodType,
                            params = params,
                            index = index
                        )
                }
            }
        }
    }
}

@Composable
fun PremiumTextEditCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    iconContentDescription: String = "Text Edit Icon",
    displayText: State<String>,
    onEditCallback: (String) -> Unit,
) {
    OutlinedTextField(
        value = displayText.value,
        onValueChange = onEditCallback,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = if (icon != null) {
            {
                Icon(
                    imageVector = icon,
                    contentDescription = iconContentDescription,
                    modifier = Modifier.size(20.dp)
                )
            }
        } else null,
        placeholder = {
            Text(
                text = "Enter phone number...",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge
    )
}