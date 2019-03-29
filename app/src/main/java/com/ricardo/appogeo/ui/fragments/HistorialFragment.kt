package com.ricardo.appogeo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import com.ricardo.appogeo.activities.MapsActivity
import com.ricardo.appogeo.ui.adapters.HistorialAdapter


class HistorialFragment : Fragment() {

    internal var empty: LinearLayout? = null
    internal var listHistory: ListView? = null

    internal lateinit var adapter: HistorialAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.ricardo.appogeo.R.layout.fragment_history, container, false)
        this.listHistory?.setEmptyView(this.empty)
        return view
    }

    fun onItemClick(i: Int) {
        val lastSearch = this.adapter.getItem(i)
        val intent = Intent(this.getActivity(), MapsActivity::class.java)
        intent.putExtra("LastSearch", lastSearch)
        this.getActivity()!!.startActivity(intent)
    }
}