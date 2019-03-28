package com.ricardo.appogeo.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.ricardo.appogeo.db.HistorialEntity
import com.ricardo.appogeo.db.HistorialRepository
import com.ricardo.appogeo.db.HistorialRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HistorialViewModel (application: Application) : AndroidViewModel(application){

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: HistorialRepository
    val allHistorial: LiveData<List<HistorialEntity>>

    init {
        val historialDao = HistorialRoomDatabase.getDatabase(application).historialDao()
        repository = HistorialRepository(historialDao)
        allHistorial = repository.allHistorialEntity
    }

    fun insert(historialEntity: HistorialEntity) = scope.launch(Dispatchers.IO) {
        repository.insert(historialEntity)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }


}