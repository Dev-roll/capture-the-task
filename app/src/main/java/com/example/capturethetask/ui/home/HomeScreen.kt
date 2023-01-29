package com.example.capturethetask.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
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
            onTaskStarredChange = viewModel::starTask,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeBody(
    itemList: List<Task>,
    onTaskClick: (String) -> Unit,
    onTaskCheckedChange: (Task, Boolean) -> Unit,
    onTaskStarredChange: (Task, Boolean) -> Unit,
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
                onTaskCheckedChange = onTaskCheckedChange,
                onTaskStarredChange = onTaskStarredChange
            )
        }
    }
}

@Composable
private fun TaskList(
    itemList: List<Task>,
    onTaskClick: (Task) -> Unit,
    onTaskCheckedChange: (Task, Boolean) -> Unit,
    onTaskStarredChange: (Task, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val listChunked = itemList.chunked(2)
    for (sweetsRowList in listChunked) {
        Row(modifier = modifier) {
            for (sweets in sweetsRowList) {
                TaskItem(
                    task = sweets,
                    onTaskClick = onTaskClick,
                    onCompletedChange = { onTaskCheckedChange(sweets, it) },
                    onStarredChange = { onTaskStarredChange(sweets, it) }
                )
            }
        }
    }

//    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
//        items(items = itemList, key = { it.id }) { item ->
//            TaskItem(
//                task = item,
//                onTaskClick = onTaskClick,
//                onCompletedChange = { onTaskCheckedChange(item, it) },
//                onStarredChange = { onTaskStarredChange(item, it) }
//            )
//            Divider()
//        }
//    }
}

@Composable
private fun TaskItem(
    task: Task,
    onTaskClick: (Task) -> Unit,
    onCompletedChange: (Boolean) -> Unit,
    onStarredChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        val screenWidth = 400.dp
        Row(modifier = modifier
            .width(screenWidth / 2)
            .clickable { onTaskClick(task) }
            .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(){IconToggleButton(
                checked = task.isCompleted,
                onCheckedChange = onCompletedChange,
            ) {
                Icon(
                    imageVector = if (task.isCompleted) Icons.Rounded.TaskAlt else Icons.Rounded.RadioButtonUnchecked,
                    contentDescription = if (task.isCompleted) "check on" else "check off",
                )
            }
            Text(
                text = task.title,
            )}
            Row(){
                Image(
                    painter = rememberAsyncImagePainter(task.filePath),
                    contentDescription = "captured image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.size(64.dp)
                )
//                Text(
//                    text = task.title,
//                )
                IconToggleButton(
                    checked = task.isStarred,
                    onCheckedChange = onStarredChange,
                ) {
                    Icon(
                        imageVector = if (task.isStarred) Icons.Rounded.Star else Icons.Rounded.StarBorder,
                        contentDescription = if (task.isStarred) "check on" else "check off",
                    )
                }
            }
        }
    }

}
