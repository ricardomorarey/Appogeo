package com.ricardo.appogeo.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ricardo.appogeo.R
import com.ricardo.appogeo.commons.HistorialBusqueda
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var historialBusqueda: HistorialBusqueda? = null
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val intent = this.intent
        if (intent != null) {
            historialBusqueda = intent.getSerializableExtra("HistorialBusqueda") as HistorialBusqueda
            textView_title.text = historialBusqueda!!.title
            tv_streetAddress.text = historialBusqueda!!.streetaddress
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (historialBusqueda != null && historialBusqueda!!.lat != 0.0 && historialBusqueda!!.lon != 0.0) {
            val sydney = LatLng(historialBusqueda!!.lat, historialBusqueda!!.lon)

            this.mMap?.run {
                addMarker(MarkerOptions().position(sydney).title(historialBusqueda!!.title))
                moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f))
            }


        }
    }
}
