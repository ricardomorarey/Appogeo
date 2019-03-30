package com.ricardo.appogeo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import com.ricardo.appogeo.R
import com.ricardo.appogeo.ui.adapters.HistorialAdapter
import com.ricardo.appogeo.db.Sqlite
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnItemClick
import com.ricardo.appogeo.activities.MapsActivity

class HistorialFragment : Fragment() {

    @BindView(R.id.listHistory)
    internal var listHistory: ListView? = null

    internal lateinit var adapter: HistorialAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dataBase = Sqlite.getInstance(this!!.context!!)
        val searches = dataBase.historialBusquedas
        this.adapter = HistorialAdapter(this.context!!, searches)
        this.listHistory?.adapter = this.adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    @OnItemClick(R.id.listHistory)
    fun onItemClick(i: Int) {
        val lastSearch = this.adapter.getItem(i)
        val intent = Intent(this.activity, MapsActivity::class.java)
        intent.putExtra("HistorialBusqueda", lastSearch)
        this.activity!!.startActivity(intent)
    }
}
