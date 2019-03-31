package com.ricardo.appogeo.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.ricardo.appogeo.R
import com.ricardo.appogeo.api.DatosMadridService
import com.ricardo.appogeo.db.Consulados
import com.ricardo.appogeo.db.HistorialBusqueda
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.Calendar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentSearch : Fragment(), Callback<Consulados> {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        return view
    }

    override fun onResponse(call: Call<Consulados>, response: Response<Consulados>?) {
        if (response != null) { }
    }

    override fun onFailure(call: Call<Consulados>, t: Throwable) {}

    fun onSearch() {
        try {
            val item = HistorialBusqueda()
            item.lon = java.lang.Double.parseDouble(editText_lon.text.toString())
            item.lat = java.lang.Double.parseDouble(editText_lat.text.toString())
            item.date = Calendar.getInstance().time
            Toast.makeText(this.context, "Añadido", Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            Toast.makeText(this.context, ex.message, Toast.LENGTH_LONG).show()
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
        call.enqueue(this)
    }
}