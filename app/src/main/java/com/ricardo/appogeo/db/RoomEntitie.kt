package com.ricardo.appogeo.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial_table")
data class RoomEntitie (
    var DATE: String,
    var Streetaddress: String,
    var Locality: String,
    var Title: String,
    var LON: String,
    var LAT: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0}

