package com.example.capturethetask.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capturethetask.CttTopAppBar
import com.example.capturethetask.R
import com.example.capturethetask.ui.AppViewModelProvider
import com.example.capturethetask.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "capture"
    override val titleRes = R.string.home_title
}

@Composable
fun HomeScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(topBar = {
        CttTopAppBar(
            title = stringResource(HomeDestination.titleRes),
            canNavigateBack = canNavigateBack,
            navigateUp = onNavigateUp
        )
    }) { innerPadding ->
        Text(text = "capture screen", modifier = modifier.padding(innerPadding))
    }
}