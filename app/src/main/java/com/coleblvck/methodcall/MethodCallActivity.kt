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
import com.coleblvck.methodcall.data.repositories.LocalChainRepository
import com.coleblvck.methodcall.data.userPreferences.methodCallDataStore
import com.coleblvck.methodcall.state.MethodCallViewModel
import com.coleblvck.methodcall.ui.NavSystem
import com.coleblvck.methodcall.ui.theme.MethodCallTheme


class MethodCallActivity : ComponentActivity() {
    private val methodCallViewModel: MethodCallViewModel by viewModels {
        MethodCallViewModel.Factory(
            LocalChainRepository(this),
            this.packageManager,
            this.methodCallDataStore
        )
    }
    private val activityTool: ActivityTool = ActivityTool(this)

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pass permission manager to ViewModel
        methodCallViewModel.setPermissionManager(activityTool.permissionManager)

        activityTool.commenceBusiness()
        enableEdgeToEdge(SystemBarStyle.dark(Color.BLACK))
        setContent {
            MethodCallTheme {
                NavSystem(methodCallViewModel = methodCallViewModel)
            }
        }
    }
}
