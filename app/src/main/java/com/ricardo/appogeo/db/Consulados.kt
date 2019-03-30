package com.ricardo.appogeo.db

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class Consulados {

    @SerializedName("@consuladosEntity")
    lateinit var consuladosEntity: ConsuladosEntity
        internal set

    @SerializedName("@graph")
    var graph = ArrayList<Obtenido>()

    fun setcontext(consuladosEntityObject: ConsuladosEntity) {
        this.consuladosEntity = consuladosEntityObject
    }
}
