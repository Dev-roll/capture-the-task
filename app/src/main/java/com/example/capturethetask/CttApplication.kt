package com.example.capturethetask

import android.app.Application
import com.example.capturethetask.model.AppContainer
import com.example.capturethetask.model.AppDataContainer

class CttApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}