package com.ricardo.appogeo.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface AppogeoDao {

    @Query("SELECT * from historial")
    fun getAllHistorial (): LiveData<List<HistorialEntity>>

    @Insert
    fun insert(historialEntity: HistorialEntity)

}