package com.ricardo.appogeo.db

import android.arch.persistence.room.PrimaryKey

class HistorialEntity(var name: String, var fecha: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}