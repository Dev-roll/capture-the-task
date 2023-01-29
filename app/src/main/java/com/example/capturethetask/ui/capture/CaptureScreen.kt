package com.example.capturethetask.ui.capture

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capturethetask.R
import com.example.capturethetask.ui.AppViewModelProvider
import com.example.capturethetask.ui.components.CttTopAppBar
import com.example.capturethetask.ui.home.HomeViewModel
import com.example.capturethetask.ui.navigation.NavigationDestination
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

object CaptureDestination : NavigationDestination {
    override val route = "capture"
    override val titleRes = R.string.capture_title
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CaptureScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateToTaskEntry: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    Scaffold(
        topBar = {
            CttTopAppBar(
                title = stringResource(CaptureDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTaskEntry,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Rounded.PhotoCamera,
                    contentDescription = stringResource(R.string.task_entry_title),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        if (cameraPermissionState.status.isGranted) {
            Text(text = "home screen", modifier = modifier.padding(innerPadding))
        } else {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}