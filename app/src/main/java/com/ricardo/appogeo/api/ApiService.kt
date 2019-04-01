package com.ricardo.appogeo.api

import com.ricardo.appogeo.db.Consulados
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.ricardo.appogeo.db.Obtenido
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

object ApiService {

    val results = ArrayList<Obtenido>()

    fun getAllData(callback: Callback<Consulados>) {

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://datos.madrid.es/egob/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val restClient = retrofit.create<DatosMadridService>(DatosMadridService::class.java!!)
        val call = restClient.loadData()
        call.enqueue(callback)
    }
}