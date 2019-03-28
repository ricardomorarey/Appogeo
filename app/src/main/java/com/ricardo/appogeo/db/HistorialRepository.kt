package com.ricardo.appogeo.db

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class HistorialRepository(private val historialDao: AppogeoDao) {

    val allHistorialEntity: LiveData<List<HistorialEntity>> = historialDao.getAllHistorial()

    @WorkerThread
    suspend  fun insert(historialEntity: HistorialEntity){
        historialDao.insert(historialEntity)
    }
}