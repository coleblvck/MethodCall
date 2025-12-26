package com.coleblvck.methodcall.ui.common.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
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
    rowHeight: Dp = 96.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(rowHeight)
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        apps.forEach { app ->
            AppCard(app = app, clickCallback = clickCallback)
        }
        Spacer(modifier = Modifier.width(12.dp))
    }
}

@Composable
fun AppCard(
    app: App,
    clickCallback: (App) -> Unit = {},
    iconOnly: Boolean = false,
    textOnly: Boolean = false,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = tween(150),
        label = "scale"
    )

    Card(
        onClick = {
            isPressed = true
            clickCallback(app)
            isPressed = false
        },
        modifier = Modifier
            .width(80.dp)
            .aspectRatio(1f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        colors = colors,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 6.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            if (!textOnly || iconOnly) {
                Surface(
                    modifier = Modifier
                        .size(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp),
                        model = app.icon,
                        contentDescription = "${app.name} icon"
                    )
                }
            }

            if (!iconOnly || textOnly) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp
                )
            }
        }
    }
}