package com.example.capturethetask.model

import android.content.Context

interface AppContainer {
    val tasksRepository: TasksRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val tasksRepository: TasksRepository by lazy {
        TasksRepositoryImpl(TaskDatabase.getDatabase(context).taskDao())
    }
}