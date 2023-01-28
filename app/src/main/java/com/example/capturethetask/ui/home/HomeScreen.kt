package com.example.capturethetask.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeBody(
    itemList: List<Task>,
    onTaskClick: (String) -> Unit,
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
            TaskList(itemList = itemList, onTaskClick = { onTaskClick(it.id) })
        }
    }
}

@Composable
private fun TaskList(
    itemList: List<Task>,
    onTaskClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items = itemList, key = { it.id }) { item ->
            TaskItem(item = item, onTaskClick = onTaskClick)
            Divider()
        }
    }
}

@Composable
private fun TaskItem(
    item: Task,
    onTaskClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Row(modifier = modifier
            .fillMaxWidth()
            .clickable { onTaskClick(item) }
            .padding(vertical = 16.dp)
        ) {
            Text(
                text = item.title,
                fontSize = 20.sp
//            modifier = Modifier.weight(1.5f),
//            fontWeight = FontWeight.Bold
            )
            var checked by remember { mutableStateOf(item.isCompleted) }
            IconToggleButton(
                checked = checked,
                onCheckedChange = { checked = it },
            ) {
                Icon(
                    imageVector = if (checked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (checked) "favorite on" else "favorite off",
                )
            }
        }
    }

}
