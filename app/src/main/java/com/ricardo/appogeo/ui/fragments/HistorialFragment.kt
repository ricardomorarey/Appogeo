package com.ricardo.appogeo.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.ricardo.appogeo.R
import com.ricardo.appogeo.activities.MapsActivity
import com.ricardo.appogeo.db.HistorialViewModel
import com.ricardo.appogeo.ui.adapters.HistorialAdapter
import kotlinx.android.synthetic.main.fragment_history.*

class HistorialFragment : Fragment() {

    internal lateinit var adapter: HistorialAdapter
    lateinit var historialViewModel: HistorialViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val searches = historialViewModel.historials
        this.adapter = HistorialAdapter(this.context!!, searches!!)
        listHistory.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        return view
    }

}
