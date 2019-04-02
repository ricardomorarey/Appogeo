package com.ricardo.appogeo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ricardo.appogeo.R
import com.ricardo.appogeo.activities.MapsActivity
import com.ricardo.appogeo.api.ApiService
import com.ricardo.appogeo.db.Consulados
import com.ricardo.appogeo.db.HistorialViewModel
import com.ricardo.appogeo.db.RoomEntitie
import com.ricardo.appogeo.ui.adapters.ObtainedAdapter
import kotlinx.android.synthetic.main.fragment_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeFragment : Fragment(), Callback<Consulados> {

    internal lateinit var adapter: ObtainedAdapter
    private lateinit var historialViewModel: HistorialViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        historialViewModel = run {
            ViewModelProviders.of(this).get(HistorialViewModel::class.java)
        }

        if (ApiService.results.size == 0) {
         //   ApiService.getAllData(this)
        }
        ApiService.getAllData(this)
        listHistory?.adapter = adapter
        listHistory.setOnItemClickListener {_, _, i, _ ->

            val item = this.adapter.getItem(i)
            val date = Calendar.getInstance().time.toString()
            val streetaddress = item!!.address.streetaddress.toString()
            val locality = item.address.locality.toString()
            val title = item.title.toString()
            val long = item.location.longitude.toString()
            val lat = item.location.latitude.toString()

            historialViewModel.saveHistorial(RoomEntitie(date, streetaddress, locality, title, long, lat))

            val intent = Intent(this.activity, MapsActivity::class.java)
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

    fun filterResults(textToSearch: String) {
        this.adapter.filter.filter(textToSearch)
        this.adapter.notifyDataSetChanged()
    }

}
