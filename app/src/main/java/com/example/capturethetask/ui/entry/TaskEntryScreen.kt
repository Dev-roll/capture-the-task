package com.example.capturethetask.ui.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddTask
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capturethetask.R
import com.example.capturethetask.ui.AppViewModelProvider
import com.example.capturethetask.ui.components.CttTopAppBar
import com.example.capturethetask.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object TaskEntryDestination : NavigationDestination {
    override val route = "task_entry"
    override val titleRes = R.string.task_entry_title
}

@Composable
fun TaskEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: TaskEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CttTopAppBar(
                title = stringResource(TaskEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        TaskEntryBody(
            taskUiState = viewModel.taskUiState,
            onTaskValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveTask()
                    navigateToHome()
                }
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TaskEntryBody(
    taskUiState: TaskUiState,
    onTaskValueChange: (TaskDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        TaskInputForm(taskDetails = taskUiState.taskDetails, onValueChange = onTaskValueChange)
        Button(
            onClick = onSaveClick,
            enabled = taskUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Icon(
                    imageVector = Icons.Rounded.AddTask,
                    contentDescription = stringResource(R.string.save_action)
                )
                Spacer(Modifier.size(8.dp))
                Text(stringResource(R.string.save_action))
            }
        }
    }
}

@Composable
fun TaskInputForm(
    taskDetails: TaskDetails,
    modifier: Modifier = Modifier,
    onValueChange: (TaskDetails) -> Unit = {},
    enabled: Boolean = true
) {
    val titleFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            value = taskDetails.title,
            onValueChange = { onValueChange(taskDetails.copy(title = it)) },
            label = { Text(stringResource(R.string.task_title_req)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Checklist, contentDescription = stringResource(
                        id = R.string.task_title_req
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(titleFocusRequester)
                .focusOrder(titleFocusRequester) {
                    next = descriptionFocusRequester
                    down = descriptionFocusRequester
                },
            enabled = enabled,
            singleLine = true,
            keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
        )
        OutlinedTextField(
            value = taskDetails.description,
            onValueChange = { onValueChange(taskDetails.copy(description = it)) },
            label = { Text(stringResource(R.string.task_description_req)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Notes, contentDescription = stringResource(
                        id = R.string.task_description_req
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(descriptionFocusRequester),
            enabled = enabled,
            singleLine = false
        )
    }

    LaunchedEffect(Unit) {
        titleFocusRequester.requestFocus()
    }
}
