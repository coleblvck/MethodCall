package com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.typeDialogContent

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType

@Composable
fun ToggleTorchDialogContent(
    dismissCallback: () -> Unit,
    chainToolBox: ChainToolBox,
    phoneNumber: State<String>,
    params: List<String>? = null,
    index: Int? = null
) {
    fun saveConfig(phone: String) {
        chainToolBox.createOrReplaceChainItem(
            phoneNumber = phone,
            method = MethodType.TOGGLE_TORCH,
        )
    }

    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        onClick = {
            saveConfig(phoneNumber.value)
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