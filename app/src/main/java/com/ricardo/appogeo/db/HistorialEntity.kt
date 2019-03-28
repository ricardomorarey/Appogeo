package com.ricardo.appogeo.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "historial")
class HistorialEntity(var name: String, var fecha: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}