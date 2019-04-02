package com.ricardo.appogeo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoHistorial {

    @Query ("SELECT * from historial_table ORDER BY DATE ASC")
    fun getAllHistorial(): List<RoomEntitie>

    @Insert
    fun insert(historial: RoomEntitie)

}