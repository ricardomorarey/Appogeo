package com.ricardo.appogeo.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

import kotlin.coroutines.CoroutineContext

class HistorialViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HistorialRepository(application)
    val historials = repository.getHistorial()

    fun saveHistorial(historial: RoomEntitie) {
        repository.insert(historial)
    }
}