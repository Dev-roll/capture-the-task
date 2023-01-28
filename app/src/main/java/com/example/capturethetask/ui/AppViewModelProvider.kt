package com.example.capturethetask.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.capturethetask.CttApplication
import com.example.capturethetask.ui.entry.TaskEntryViewModel
import com.example.capturethetask.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { TaskEntryViewModel(cttApplication().container.tasksRepository) }

        initializer { HomeViewModel(cttApplication().container.tasksRepository) }
    }

}

fun CreationExtras.cttApplication(): CttApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CttApplication)