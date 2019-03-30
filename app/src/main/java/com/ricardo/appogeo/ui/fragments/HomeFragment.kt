package com.ricardo.appogeo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import com.ricardo.appogeo.activities.MapsActivity
import com.ricardo.appogeo.api.ApiService
import com.ricardo.appogeo.db.*
import com.ricardo.appogeo.ui.adapters.ObtainedAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), Callback<Consulados> {

    private val results = ArrayList<Obtenido>()
    private val api = ApiService()

    internal lateinit var adapter: ObtainedAdapter
    internal var empty: LinearLayout? = null
    internal var listHistory: ListView? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val callback = ConsuladosEntity()
        if (results.size === 0) api.getAllData(callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.ricardo.appogeo.R.layout.fragment_history, container, false)
        this.listHistory?.setEmptyView(this.empty)
        this.adapter = ObtainedAdapter(this.getContext()!!, results)
        this.listHistory?.setAdapter(adapter)
        return view
    }

    override fun onFailure(call: Call<Consulados>, t: Throwable) {
        try {
            Toast.makeText(this.getContext(), "Error de conexion", Toast.LENGTH_LONG).show()
        } catch (ignore: Exception) { }
    }

    fun onItemClick(i: Int) {
        val item = this.adapter.getItem(i)
        val dataBase = Sqlite()
        val lastSearch = HistorialBusqueda()
        lastSearch._id
        lastSearch.title
        lastSearch.locality
        lastSearch.streetaddress

        if (item!!.location != null) {
            lastSearch.lat
            lastSearch.lon
        }

        lastSearch.date

        dataBase.upsert(lastSearch)
        val intent = Intent(this.getActivity(), MapsActivity::class.java)
        intent.putExtra("LastSearch", lastSearch)
        this.getActivity()!!.startActivity(intent)
    }

    override fun onResponse(call: Call<Consulados>, response: Response<Consulados>?) {
        if (response != null && response!!.body() != null && response!!.body()!!.graph != null) {
            results.clear()
            results.addAll(response!!.body()!!.graph)
            this.adapter.notifyDataSetChanged()
        }
    }

    fun filterResults(textToSearch: String) {
        this.adapter.getFilter().filter(textToSearch)
        this.adapter.notifyDataSetChanged()
    }

    companion object {
        private val results = ArrayList<Obtenido>()
    }
}