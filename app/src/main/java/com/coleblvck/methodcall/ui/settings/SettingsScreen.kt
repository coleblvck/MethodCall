package com.coleblvck.methodcall.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    toggleSuperUserPrivilege: (Boolean) -> Unit,
    superUserPrivilegeValue: State<Boolean>
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) { innerPadding ->
        val headingFontSize = 32.sp
        val headingFontWeight = FontWeight(600)
        val subNormalFontWeight = FontWeight(400)
        val normalFontSize = 20.sp
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Settings", fontSize = headingFontSize, fontWeight = headingFontWeight)
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Superuser",
                        fontSize = headingFontSize,
                        fontWeight = headingFontWeight
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Status", fontSize = normalFontSize, fontWeight = subNormalFontWeight)
                        Switch(checked = superUserPrivilegeValue.value, onCheckedChange = toggleSuperUserPrivilege)
                    }
                }
            }
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
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
                        "About",
                        fontSize = headingFontSize,
                        fontWeight = headingFontWeight
                    )
                    ElevatedCard(
                        onClick = {
                            context.startActivity(webSearchIntent)
                        }) {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = "View on GitHub",
                            fontWeight = subNormalFontWeight
                        )
                    }
                }
            }
        }
    }
}