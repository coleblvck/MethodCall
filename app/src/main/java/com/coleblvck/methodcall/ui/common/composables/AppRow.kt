package com.coleblvck.methodcall.ui.common.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.coleblvck.methodcall.data.appPackage.App

@Composable
fun AppRow(
    apps: List<App>,
    clickCallback: (App) -> Unit = {},
    rowHeight: Dp = 80.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(rowHeight)
            .horizontalScroll(
                state = rememberScrollState()
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        apps.forEach {
            AppCard(app = it, clickCallback = clickCallback)
        }
    }
}

@Composable
fun AppCard(
    app: App,
    clickCallback: (App) -> Unit = {},
    iconOnly: Boolean = false,
    textOnly: Boolean = false,
    colors: CardColors = CardDefaults.elevatedCardColors(
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.onTertiary
    )
) {
    ElevatedCard(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable {
                clickCallback(app)
            },
        colors = colors
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            if (!textOnly || iconOnly)
                AsyncImage(
                    modifier = Modifier
                        .weight(1f),
                    model = app.icon,
                    contentDescription = "${app.name} icon"
                )
            if (!iconOnly || textOnly)
                Text(
                    text = app.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
        }
    }
}