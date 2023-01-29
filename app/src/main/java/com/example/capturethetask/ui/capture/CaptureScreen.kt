package com.example.capturethetask.ui.capture

import android.os.Build
import android.os.ext.SdkExtensions.getExtensionVersion
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
                    handlePhotoPickerLaunch()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Image,
                        contentDescription = stringResource(id = R.string.pick_button)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        Text(text = "home screen", modifier = modifier.padding(innerPadding))
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun isPhotoPickerAvailable(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        true
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        getExtensionVersion(Build.VERSION_CODES.R) >= 2
    } else {
        false
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun handlePhotoPickerLaunch() {
    if (isPhotoPickerAvailable()) {
        println("working")
        // To launch the system photo picker, invoke an intent that includes the
        // ACTION_PICK_IMAGES action. Consider adding support for the
        // EXTRA_PICK_IMAGES_MAX intent extra.
        MainActivity().pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//                    MainActivity().selectImage()
    } else {
        // Consider implementing fallback functionality so that users can still
        // select images and videos.
        println("NOT working")
    }
}
