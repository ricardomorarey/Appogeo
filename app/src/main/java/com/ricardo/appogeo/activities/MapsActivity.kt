package com.ricardo.appogeo.activities

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ricardo.appogeo.R
import com.ricardo.appogeo.db.HistorialBusqueda

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    @BindView(R.id.tv_streetAddress)
    internal var tv_streetAddress: TextView? = null
    @BindView(R.id.textView_title)
    internal var tv_title: TextView? = null
    private var historialBusqueda: HistorialBusqueda? = null
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        ButterKnife.bind(this)

        val intent = this.intent
        if (intent != null) {
            historialBusqueda = intent.getSerializableExtra("HistorialBusqueda") as HistorialBusqueda
            tv_title!!.text = historialBusqueda!!.title
            tv_streetAddress!!.text = historialBusqueda!!.streetaddress
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (historialBusqueda != null && historialBusqueda!!.lat != 0.0 && historialBusqueda!!.lon != 0.0) {
            val sydney = LatLng(historialBusqueda!!.lat, historialBusqueda!!.lon)
            mMap!!.addMarker(MarkerOptions().position(sydney).title(historialBusqueda!!.title))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            mMap!!.moveCamera(CameraUpdateFactory.zoomTo(17f))
        }
    }
}
