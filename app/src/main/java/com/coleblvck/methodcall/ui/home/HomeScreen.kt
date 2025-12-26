package com.coleblvck.methodcall.ui.home

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.Chain
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.permissions.PermissionManager
import com.coleblvck.methodcall.ui.home.chainListColumn.ChainListColumn
import com.coleblvck.methodcall.ui.home.methodTypeListColumn.MethodTypeListColumn
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    chainToolBox: ChainToolBox,
    liveChains: LiveData<List<Chain>>,
    apps: State<List<App>>,
    getChainItemParameterName: (methodType: MethodType, parameter: String) -> String,
    superuserIsEnabled: State<Boolean>,
    permissionManager: PermissionManager? = null,
    homePagerState: PagerState = rememberPagerState(initialPage = 0, pageCount = { 2 }),
    navigateToSettings: () -> Unit
) {
    val statusBarLight = MaterialTheme.colorScheme.background.toArgb()
    val statusBarDark = MaterialTheme.colorScheme.background.toArgb()
    val navigationBarLight = MaterialTheme.colorScheme.background.toArgb()
    val navigationBarDark = MaterialTheme.colorScheme.background.toArgb()
    val isDarkMode = isSystemInDarkTheme()
    val context = LocalContext.current as ComponentActivity

    context.enableEdgeToEdge(
        statusBarStyle = if (!isDarkMode) {
            SystemBarStyle.light(statusBarLight, Color.Black.toArgb())
        } else {
            SystemBarStyle.dark(statusBarDark)
        },
        navigationBarStyle = if (!isDarkMode) {
            SystemBarStyle.light(navigationBarLight, Color.Black.toArgb())
        } else {
            SystemBarStyle.dark(navigationBarDark)
        }
    )

    val coroutineScope = rememberCoroutineScope()

    fun updateHomePagerPage(page: Int) {
        if (homePagerState.currentPage != page) {
            coroutineScope.launch { homePagerState.scrollToPage(page) }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Premium header
                PremiumHeader(
                    currentPage = homePagerState.currentPage,
                    onSettingsClick = navigateToSettings
                )

                // Content pager
                HorizontalPager(
                    state = homePagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    when (page) {
                        0 -> MethodTypeListColumn(
                            chainToolBox = chainToolBox,
                            apps = apps,
                            superuserIsEnabled = superuserIsEnabled,
                            permissionManager = permissionManager
                        )
                        1 -> ChainListColumn(
                            chainToolBox = chainToolBox,
                            liveChains = liveChains,
                            getChainItemParameterName = getChainItemParameterName
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }

            // Floating navigation
            PremiumBottomNav(
                pagerState = homePagerState,
                updatePagerState = ::updateHomePagerPage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
fun PremiumHeader(
    currentPage: Int,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Page title with fade animation
        Column {
            Text(
                text = when (currentPage) {
                    0 -> "Actions"
                    else -> "Chains"
                },
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = when (currentPage) {
                    0 -> "Build your automation"
                    else -> "Manage sequences"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Settings button
        FilledIconButton(
            onClick = onSettingsClick,
            modifier = Modifier.size(48.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun PremiumBottomNav(
    pagerState: PagerState,
    updatePagerState: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .height(64.dp),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f),
        tonalElevation = 3.dp,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Home,
                label = "Actions",
                isSelected = pagerState.currentPage == 0,
                onClick = { updatePagerState(0) }
            )

            NavItem(
                modifier = Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.List,
                label = "Chains",
                isSelected = pagerState.currentPage == 1,
                onClick = { updatePagerState(1) }
            )
        }
    }
}

@Composable
fun NavItem(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateDpAsState(
        targetValue = if (isSelected) 16.dp else 0.dp,
        animationSpec = tween(300),
        label = "background"
    )

    Surface(
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            Color.Transparent
        }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(20.dp),
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                }
            )

            if (isSelected) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}