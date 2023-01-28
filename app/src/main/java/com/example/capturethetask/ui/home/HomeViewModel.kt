package com.example.capturethetask.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capturethetask.model.Task
import com.example.capturethetask.model.TasksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val tasksRepository: TasksRepository) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        tasksRepository.getAllTasksStream().map { HomeUiState(it) }.stateIn(
            scope = viewModelScope, started = SharingStarted.WhileSubscribed(
                TIMEOUT_MILLIS
            ), initialValue = HomeUiState()
        )

    fun completeTask(task: Task, completed: Boolean) = viewModelScope.launch {
        if (completed) {
            tasksRepository.completeTask(task)
        } else {
            tasksRepository.activateTask(task)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val itemList: List<Task> = listOf())