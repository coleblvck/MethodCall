package com.coleblvck.methodcall.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coleblvck.methodcall.state.MethodCallViewModel
import com.coleblvck.methodcall.ui.home.HomeScreen
import com.coleblvck.methodcall.ui.settings.SettingsScreen
import kotlinx.serialization.Serializable


@Serializable
object Home

@Serializable
object Settings

@Composable
fun NavSystem(methodCallViewModel: MethodCallViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(
                chainToolBox = methodCallViewModel.chainToolBox,
                liveChains = methodCallViewModel.liveChains,
                apps = methodCallViewModel.appState.apps,
                getChainItemParameterName = methodCallViewModel.getChainItemParameterName,
                navigateToSettings = { navController.navigate(route = Settings) },
            )
        }
        composable<Settings> {
            SettingsScreen(
                toggleSuperUserPrivilege = methodCallViewModel.superUserTools.toggleSuperUserPrivileges,
                methodCallViewModel.userPreferencesToolBox.userPreferences.superuserEnabled
            )

        }
    }
}