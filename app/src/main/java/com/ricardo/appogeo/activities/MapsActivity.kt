package com.ricardo.appogeo.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ricardo.appogeo.db.HistorialBusqueda


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    var tv_streetAddress: TextView? = null
    var tv_title: TextView? = null
    private var lastSearch: HistorialBusqueda? = null
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ricardo.appogeo.R.layout.activity_maps)

        val intent = this.intent
        if (intent != null) {
            lastSearch = intent.getSerializableExtra("LastSearch") as HistorialBusqueda
            tv_title!!.setText(lastSearch!!.title)
            tv_streetAddress!!.setText(lastSearch!!.streetaddress)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(com.ricardo.appogeo.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (lastSearch != null && lastSearch!!.lat !== 0.0 && lastSearch!!.lon !== 0.0) {
            val sydney = LatLng(lastSearch!!.lat, lastSearch!!.lon)
            mMap!!.addMarker(MarkerOptions().position(sydney).title(lastSearch!!.title))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            mMap!!.moveCamera(CameraUpdateFactory.zoomTo(17f))
        }
    }
}
