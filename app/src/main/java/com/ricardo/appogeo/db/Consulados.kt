package com.ricardo.appogeo.db

import com.google.gson.annotations.SerializedName

class Consulados {

    @SerializedName("@context")
    lateinit var context: ConsuladosEntity
        internal set

    @SerializedName("@graph")
    var graph = ArrayList<Obtenido>()

    fun setcontext(contextObject: ConsuladosEntity) {
        this.context = contextObject
    }
}