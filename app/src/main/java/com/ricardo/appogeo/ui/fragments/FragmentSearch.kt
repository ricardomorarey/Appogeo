package com.ricardo.appogeo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ricardo.appogeo.R
import com.ricardo.appogeo.api.ApiService
import com.ricardo.appogeo.commons.ConsuladoLAT
import com.ricardo.appogeo.commons.Consulados
import com.ricardo.appogeo.commons.HistorialBusqueda
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

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

    fun onSearch(lat: ConsuladoLAT,lon: ConsuladoLAT,callback: Callback<Consulados>) {
        try {
            val item = HistorialBusqueda()
            item.lon = java.lang.Double.parseDouble(editText_lon.text.toString())
            item.lat = java.lang.Double.parseDouble(editText_lat.text.toString())
            item.date = Calendar.getInstance().time
            Toast.makeText(this.context, "Añadido", Toast.LENGTH_LONG).show()
            ApiService.getAllData(lat,lon,callback)
        } catch (ex: Exception) {
            Toast.makeText(this.context, ex.message, Toast.LENGTH_LONG).show()
        }
    }


}