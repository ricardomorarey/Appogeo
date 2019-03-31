package com.ricardo.appogeo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ricardo.appogeo.R
import com.ricardo.appogeo.activities.MapsActivity
import com.ricardo.appogeo.db.Sqlite
import com.ricardo.appogeo.ui.adapters.HistorialAdapter
import kotlinx.android.synthetic.main.fragment_history.*

class HistorialFragment : Fragment() {

    internal lateinit var adapter: HistorialAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dataBase = Sqlite.getInstance(this!!.context!!)
        val searches = dataBase.historialBusquedas
        this.adapter = HistorialAdapter(this.context!!, searches)
        listHistory.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        return view
    }

    fun onItemClick(i: Int) {
        val lastSearch = this.adapter.getItem(i)
        val intent = Intent(this.activity, MapsActivity::class.java)
        intent.putExtra("HistorialBusqueda", lastSearch)
        this.activity!!.startActivity(intent)
    }
}
