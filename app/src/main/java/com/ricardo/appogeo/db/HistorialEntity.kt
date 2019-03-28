package com.ricardo.appogeo.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "historial")
class HistorialEntity(var name: String, var fecha: Date) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}