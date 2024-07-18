package com.coleblvck.methodcall.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.Chain
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.ui.home.chainListColumn.ChainListColumn
import com.coleblvck.methodcall.ui.home.methodTypeListColumn.MethodTypeListColumn


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(
    chainToolBox: ChainToolBox,
    liveChains: LiveData<List<Chain>>,
    getChainItemParameterName: (MethodType, String) -> String,
    apps: State<List<App>>,
    homePagerState: PagerState = rememberPagerState(initialPage = 0, pageCount = { 2 }),
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        containerColor = Color.Black,
        contentColor = Color.White,
    ) { innerPadding ->
        HorizontalPager(
            state = homePagerState,
            modifier = Modifier.padding(innerPadding)
        ) { page ->
            when (page) {
                0 -> MethodTypeListColumn(chainToolBox = chainToolBox, apps = apps)
                1 -> ChainListColumn(
                    chainToolBox = chainToolBox,
                    liveChains = liveChains,
                    getChainItemParameterName = getChainItemParameterName
                )
            }
        }
    }
}