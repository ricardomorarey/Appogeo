package com.ricardo.appogeo.api

import com.ricardo.appogeo.db.Consulados
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

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