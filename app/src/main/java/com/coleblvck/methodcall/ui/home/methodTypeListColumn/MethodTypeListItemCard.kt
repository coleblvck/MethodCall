package com.coleblvck.methodcall.ui.home.methodTypeListColumn

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.methodType.helpers.getLegibleMethodTypeName
import com.coleblvck.methodcall.methodType.methodIcon
import com.coleblvck.methodcall.permissions.PermissionManager
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.MethodAddEditDialog
import kotlinx.coroutines.launch

@Composable
fun MethodTypeListItemCard(
    chainToolBox: ChainToolBox,
    methodType: MethodType,
    apps: State<List<App>>,
    permissionManager: PermissionManager? = null,
    modifier: Modifier = Modifier
) {
    var addDialogVisible by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val hasPermissions = remember(permissionManager, methodType) {
        permissionManager?.hasAllPermissions(methodType) ?: true
    }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(200),
        label = "scale"
    )

    fun onCardClick() {
        if (permissionManager != null) {
            permissionManager.checkAndRequestPermission(methodType) { granted ->
                if (granted) {
                    addDialogVisible = true
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Permission required for ${getLegibleMethodTypeName(methodType)}",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        } else {
            // Fallback if permission manager not available
            addDialogVisible = true
        }
    }

    Box {
        when {
            addDialogVisible -> MethodAddEditDialog(
                dismissCallback = { addDialogVisible = false },
                chainToolBox = chainToolBox,
                methodType = methodType,
                apps = apps
            )
        }

        Card(
            onClick = {
                isPressed = true
                onCardClick()
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 6.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp,
                pressedElevation = 4.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = methodIcon(methodType),
                        contentDescription = "Action Icon",
                        modifier = Modifier.height(60.dp).width(60.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = getLegibleMethodTypeName(methodType),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = (-0.3).sp
                        )

                        Text(
                            text = if (hasPermissions) "Add to chain" else "Permission required",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = if (hasPermissions) 0.6f else 0.8f
                            ),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                if (!hasPermissions) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Permission Required",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                } else {
                    Surface(
                        modifier = Modifier.size(8.dp),
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    ) {}
                }
            }
        }

        // Snackbar for permission denied
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        LaunchedEffect(isPressed) {
            if (isPressed) {
                kotlinx.coroutines.delay(200)
                isPressed = false
            }
        }
    }
}