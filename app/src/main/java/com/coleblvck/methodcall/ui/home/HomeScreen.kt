package com.coleblvck.methodcall.ui.home

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.Chain
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.ui.common.composables.ConditionalColorButtonCard
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
    homePagerState: PagerState = rememberPagerState(initialPage = 0, pageCount = { 2 }),
    navigateToSettings: () -> Unit
) {


    //

    val statusBarLight = MaterialTheme.colorScheme.background.toArgb()
    val statusBarDark = MaterialTheme.colorScheme.background.toArgb()
    val navigationBarLight = MaterialTheme.colorScheme.background.toArgb()
    val navigationBarDark = MaterialTheme.colorScheme.background.toArgb()
    val isDarkMode = isSystemInDarkTheme()
    val context = LocalContext.current as ComponentActivity

    context.enableEdgeToEdge(
        statusBarStyle = if (!isDarkMode) {
            SystemBarStyle.light(
                statusBarLight,
                Color.Black.toArgb()
            )
        } else {
            SystemBarStyle.dark(
                statusBarDark
            )
        },
        navigationBarStyle = if (!isDarkMode) {
            SystemBarStyle.light(
                navigationBarLight,
                Color.Black.toArgb()
            )
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(onClick = { navigateToSettings() }) {
                    Icon(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .size(30.dp)
                            .padding(8.dp),
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings Page Button",
                    )
                }
            }
            HorizontalPager(
                state = homePagerState,
                modifier = Modifier
                    .weight(1f)
            ) { page ->
                when (page) {
                    0 -> MethodTypeListColumn(
                        chainToolBox = chainToolBox,
                        apps = apps,
                        superuserIsEnabled = superuserIsEnabled
                    )

                    1 -> ChainListColumn(
                        chainToolBox = chainToolBox,
                        liveChains = liveChains,
                        getChainItemParameterName = getChainItemParameterName
                    )
                }
            }
            BottomPagerNav(pagerState = homePagerState, updatePagerState = ::updateHomePagerPage)
        }
    }
}


@Composable
fun BottomPagerNav(
    pagerState: PagerState,
    updatePagerState: (Int) -> Unit,
) {

    ElevatedCard(
        modifier = Modifier
            .padding(12.dp)
            .height(80.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ConditionalColorButtonCard(
                modifier = Modifier.weight(1f),
                selectionColorCondition = pagerState.currentPage == 0,
                clickAction = { updatePagerState(0) }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .size(30.dp)
                            .padding(8.dp)
                            .align(Alignment.Center),
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home Button Icon",
                    )
                }
            }
            ConditionalColorButtonCard(
                modifier = Modifier.weight(1f),
                selectionColorCondition = pagerState.currentPage == 1,
                clickAction = { updatePagerState(1) }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .size(30.dp)
                            .padding(8.dp)
                            .align(Alignment.Center),
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = "Method Chain List Button Icon",
                    )
                }
            }
        }
    }
}