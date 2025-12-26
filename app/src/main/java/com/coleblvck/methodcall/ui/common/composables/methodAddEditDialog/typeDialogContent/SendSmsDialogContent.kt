package com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType

@Composable
fun SendSmsDialogContent(
    dismissCallback: () -> Unit,
    chainToolBox: ChainToolBox,
    phoneNumber: State<String>,
    params: List<String>? = null,
    index: Int? = null
) {
    val smsPhoneNumber = remember { mutableStateOf(params?.getOrNull(0) ?: "") }
    val smsMessage = remember { mutableStateOf(params?.getOrNull(1) ?: "") }

    fun saveConfig(phone: String) {
        val args = mutableListOf(smsPhoneNumber.value, smsMessage.value)
        chainToolBox.createOrReplaceChainItem(
            phoneNumber = phone,
            method = MethodType.SEND_SMS,
            params = args,
            chainReplacementIndex = index
        )
    }

    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(150),
        label = "scale"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // SMS Phone Number Input
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Recipient Phone Number",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            OutlinedTextField(
                value = smsPhoneNumber.value,
                onValueChange = { smsPhoneNumber.value = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone",
                        modifier = Modifier.size(20.dp)
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter recipient number...",
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

        // SMS Message Input
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Message",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            OutlinedTextField(
                value = smsMessage.value,
                onValueChange = { smsMessage.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Message",
                        modifier = Modifier.size(20.dp)
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter message text...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                ),
                maxLines = 4,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = {
                isPressed = true
                saveConfig(phoneNumber.value)
                dismissCallback()
            },
            enabled = smsPhoneNumber.value.isNotBlank() && smsMessage.value.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp,
                pressedElevation = 6.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Save Action",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}