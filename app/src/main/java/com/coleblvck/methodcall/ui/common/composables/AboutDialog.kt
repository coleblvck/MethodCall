package com.coleblvck.methodcall.ui.common.composables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType

@Composable
fun AboutDialog(
    dismissCallback: () -> Unit,
) {
    val context = LocalContext.current
    Dialog(onDismissRequest = dismissCallback) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                val url = "https://github.com/coleblvck/MethodCall"
                val webSearchIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                Text(
                    text = "Method Call - Cole Blvck",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600
                )
                ElevatedCard(
                    onClick = {
                    context.startActivity(webSearchIntent)
                }) {
                    Text(modifier = Modifier.padding(12.dp), text = "View on GitHub")
                }
            }
        }
    }
}