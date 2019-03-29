package com.ricardo.appogeo.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.ricardo.appogeo.api.DatosMadridService
import com.ricardo.appogeo.db.Consulados
import com.ricardo.appogeo.db.HistorialBusqueda
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentSearch : Fragment(), Callback<Consulados> {

    internal var ed_lat: EditText? = null
    internal var ed_lon: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.ricardo.appogeo.R.layout.fragment_search, container, false)
        return view
    }

    override fun onResponse(call: Call<Consulados>, response: Response<Consulados>?) {
        if (response != null) {
        }
    }

    override fun onFailure(call: Call<Consulados>, t: Throwable) {
    }

    fun onSearch() {
        try {
            val item = HistorialBusqueda()
            item.lon
            item.lat
            item.date
            Toast.makeText(this.getContext(), "Añadida correctamente", Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            Toast.makeText(this.getContext(), ex.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun callerApi() {

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://datos.madrid.es/egob/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val restClient = retrofit.create<DatosMadridService>(DatosMadridService::class.java!!)
        val call = restClient.loadData()
        call.enqueue()
    }
}

private fun <T> Call<T>.enqueue() {}
