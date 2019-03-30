package com.ricardo.appogeo.ui.fragments

import java.util.ArrayList
import java.util.Calendar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnItemClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import com.ricardo.appogeo.R
import com.ricardo.appogeo.activities.MapsActivity
import com.ricardo.appogeo.ui.adapters.ObtainedAdapter
import com.ricardo.appogeo.db.Consulados
import com.ricardo.appogeo.db.HistorialBusqueda
import com.ricardo.appogeo.db.Obtenido
import com.ricardo.appogeo.db.Sqlite
import com.ricardo.appogeo.api.ApiService

class HomeFragment : Fragment(), Callback<Consulados> {

    internal lateinit var adapter: ObtainedAdapter
    @BindView(R.id.listHistory)
    internal var listHistory: ListView? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (results.size == 0) {
            ApiService.getAllData(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        ButterKnife.bind(this, view)
        this.adapter = ObtainedAdapter(this.context!!, results)
        this.listHistory?.adapter = adapter
        return view
    }

    override fun onFailure(call: Call<Consulados>, t: Throwable) {
        try {
            Toast.makeText(this.context, "Error de conexion", Toast.LENGTH_LONG).show()
        } catch (ignore: Exception) { }
    }

    @OnItemClick(R.id.listHistory)
    fun onItemClick(i: Int) {
        val item = this.adapter.getItem(i)
        val dataBase = Sqlite.getInstance(this!!.context!!)
        val lastSearch = HistorialBusqueda()
        lastSearch._id = Integer.valueOf(item!!.id!!)
        lastSearch.title = item.title
        lastSearch.locality = item.address.locality
        lastSearch.streetaddress = item.address.streetaddress

        if (item.location != null) {
            lastSearch.lat = item.location.latitude.toDouble()
            lastSearch.lon = item.location.longitude.toDouble()
        }

        lastSearch.date = Calendar.getInstance().time

        dataBase.upsert(lastSearch)
        val intent = Intent(this.activity, MapsActivity::class.java)
        intent.putExtra("HistorialBusqueda", lastSearch)
        this.activity!!.startActivity(intent)
    }

    override fun onResponse(call: Call<Consulados>, response: Response<Consulados>?) {
        if (response != null && response.body() != null && response.body()!!.graph != null) {
            results.clear()
            results.addAll(response.body()!!.graph)
            this.adapter.notifyDataSetChanged()
        }
    }

    fun filterResults(textToSearch: String) {
        this.adapter.filter.filter(textToSearch)
        this.adapter.notifyDataSetChanged()
    }

    companion object { private val results = ArrayList<Obtenido>() }
}
