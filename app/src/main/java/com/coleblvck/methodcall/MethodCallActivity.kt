package com.coleblvck.methodcall

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Observer
import com.coleblvck.methodcall.data.chain.Chain
import com.coleblvck.methodcall.data.repositories.LocalChainRepository
import com.coleblvck.methodcall.state.MethodCallViewModel
import com.coleblvck.methodcall.ui.home.Home
import com.coleblvck.methodcall.ui.theme.MethodCallTheme


class MethodCallActivity : ComponentActivity() {
    private val methodCallViewModel: MethodCallViewModel by viewModels {
        MethodCallViewModel.Factory(
            LocalChainRepository(this),
            this.packageManager
        )
    }
    private val activityTool: ActivityTool = ActivityTool(this)

    @RequiresApi(Build.VERSION_CODES.Q)
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTool.commenceBusiness()
        enableEdgeToEdge(SystemBarStyle.dark(Color.BLACK))
        setContent {
            MethodCallTheme {
                Home(
                    chainToolBox = methodCallViewModel.chainToolBox,
                    liveChains = methodCallViewModel.liveChains,
                    apps = methodCallViewModel.appState.apps,
                    getChainItemParameterName = methodCallViewModel.getChainItemParameterName
                )
            }
        }
    }
}

