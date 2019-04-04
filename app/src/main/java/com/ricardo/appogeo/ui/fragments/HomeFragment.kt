package com.ricardo.appogeo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ricardo.appogeo.R
import com.ricardo.appogeo.activities.MapsActivity
import com.ricardo.appogeo.api.ApiService
import com.ricardo.appogeo.commons.Consulados
import com.ricardo.appogeo.commons.HistorialBusqueda
import com.ricardo.appogeo.db.Sqlite
import com.ricardo.appogeo.adapters.ObtainedAdapter
import com.ricardo.appogeo.commons.Obtenido
import kotlinx.android.synthetic.main.fragment_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeFragment : Fragment(), Callback<Consulados> {

    private lateinit var adapter: ObtainedAdapter
    private lateinit var dataBase: Sqlite

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (ApiService.results.size == 0) {
         //   ApiService.getAllData(this)
        }
      this.dataBase = Sqlite.getInstance(this.context!!)

        ApiService.getAllData(this)
        listHistory?.adapter = adapter
        listHistory.setOnItemClickListener {ad, _, i, _ ->

            val item = ad.getItemAtPosition(i) as Obtenido

            val lastSearch = HistorialBusqueda()
            lastSearch._id = Integer.valueOf(item.id!!)
            lastSearch.title = item.title
            lastSearch.locality = item.address.locality
            lastSearch.streetaddress = item.address.streetaddress
            lastSearch.lat = item.location.latitude.toDouble()
            lastSearch.lon = item.location.longitude.toDouble()
            lastSearch.date = Calendar.getInstance().time
            dataBase.upsert(lastSearch)

            val intent = Intent(this.activity, MapsActivity::class.java)
            intent.putExtra("HistorialBusqueda", lastSearch)
            this.activity!!.startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        this.adapter = ObtainedAdapter(this.context!!, ApiService.results)

        return view
    }

    override fun onFailure(call: Call<Consulados>, t: Throwable) {
        try {
            Toast.makeText(this.context, "Error de conexion", Toast.LENGTH_LONG).show()
        } catch (ignore: Exception) { }
    }

    override fun onResponse(call: Call<Consulados>, response: Response<Consulados>?) {
        response?.body()?.graph?.run {
            ApiService.results.clear()
            ApiService.results.addAll(this)
            adapter.notifyDataSetChanged()
        }
    }

    /**
     *  se usa como metodo de la clase datos  desde la clase main
    */
    fun filterResults(textToSearch: String) {
        this.adapter.filter.filter(textToSearch)
        this.adapter.notifyDataSetChanged()
    }

}
