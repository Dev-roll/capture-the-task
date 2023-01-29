package com.example.capturethetask.ui.capture

import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capturethetask.MainActivity
import com.example.capturethetask.R
import com.example.capturethetask.ui.AppViewModelProvider
import com.example.capturethetask.ui.components.CttTopAppBar
import com.example.capturethetask.ui.home.HomeViewModel
import com.example.capturethetask.ui.navigation.NavigationDestination

object CaptureDestination : NavigationDestination {
    override val route = "capture"
    override val titleRes = R.string.capture_title
}

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
    Scaffold(
        topBar = {
            CttTopAppBar(
                title = stringResource(CaptureDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            Row {
                FloatingActionButton(
                    onClick = navigateToTaskEntry,
                    modifier = Modifier.navigationBarsPadding()
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PhotoCamera,
                        contentDescription = stringResource(R.string.capture_button),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                IconButton(onClick = { /*TODO*/
                    MainActivity().selectImage()
                }) {
                    Icon(imageVector = Icons.Rounded.Image, contentDescription = stringResource(id = R.string.pick_button))
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        Text(text = "home screen", modifier = modifier.padding(innerPadding))
    }
}