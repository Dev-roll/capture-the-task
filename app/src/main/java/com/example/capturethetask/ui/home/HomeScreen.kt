package com.example.capturethetask.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capturethetask.R
import com.example.capturethetask.model.Task
import com.example.capturethetask.ui.AppViewModelProvider
import com.example.capturethetask.ui.components.CttTopAppBar
import com.example.capturethetask.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home_title
}

@Composable
fun HomeScreen(
    navigateToCapture: () -> Unit,
    navigateToTaskUpdate: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    Scaffold(
        topBar = {
            CttTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToCapture,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.task_entry_title),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            itemList = homeUiState.itemList,
            onTaskClick = navigateToTaskUpdate,
            onTaskCheckedChange = viewModel::completeTask,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeBody(
    itemList: List<Task>,
    onTaskClick: (String) -> Unit,
    onTaskCheckedChange: (Task, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (itemList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                style = MaterialTheme.typography.subtitle2
            )
        } else {
            TaskList(
                itemList = itemList,
                onTaskClick = { onTaskClick(it.id) },
                onTaskCheckedChange = onTaskCheckedChange
            )
        }
    }
}

@Composable
private fun TaskList(
    itemList: List<Task>,
    onTaskClick: (Task) -> Unit,
    onTaskCheckedChange: (Task, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items = itemList, key = { it.id }) { item ->
            TaskItem(
                task = item,
                onTaskClick = onTaskClick,
                onCheckedChange = { onTaskCheckedChange(item, it) })
            Divider()
        }
    }
}

@Composable
private fun TaskItem(
    task: Task,
    onTaskClick: (Task) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Row(modifier = modifier
            .fillMaxWidth()
            .clickable { onTaskClick(task) }
            .padding(vertical = 16.dp)
        ) {
            Text(
                text = item.title,
            )
//            val checked by remember { mutableStateOf(task.isCompleted) }
            IconToggleButton(
                checked = task.isCompleted,
                onCheckedChange = onCheckedChange,
            ) {
                Icon(
                    imageVector = if (task.isCompleted) Icons.Rounded.TaskAlt else Icons.Rounded.RadioButtonUnchecked,
                    contentDescription = if (task.isCompleted) "check on" else "check off",
                )
            }
        }
    }

}
