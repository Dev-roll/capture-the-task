package com.example.capturethetask.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.capturethetask.ui.capture.CaptureDestination
import com.example.capturethetask.ui.capture.CaptureScreen
import com.example.capturethetask.ui.entry.TaskEntryDestination
import com.example.capturethetask.ui.entry.TaskEntryScreen
import com.example.capturethetask.ui.home.HomeDestination
import com.example.capturethetask.ui.home.HomeScreen

@Composable
fun CttNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = TaskEntryDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = TaskEntryDestination.route) {
            TaskEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = CaptureDestination.route) {
            CaptureScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}