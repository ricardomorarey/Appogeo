package com.ricardo.appogeo.api

import com.ricardo.appogeo.db.ConsuladosEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DatosMadridSevice {

    @GET("201000-0-embajadas-consulados.json")
    fun searchEmbajadasConsulados(): Call<List<ConsuladosEntity>>

    @GET("201000-0-embajadasconsulados.\n" +
            "json?{latitud}&{longitud}&distancia=1000")
    fun getECLatLong(@Path("latitud") latitud: Long, @Path("longitud") longitud: Long):Call<List<ConsuladosEntity>>

}