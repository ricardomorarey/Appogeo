package com.ricardo.appogeo.commons

import com.google.gson.annotations.SerializedName
import com.ricardo.appogeo.commons.ConsuladosEntity
import com.ricardo.appogeo.commons.Obtenido

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
