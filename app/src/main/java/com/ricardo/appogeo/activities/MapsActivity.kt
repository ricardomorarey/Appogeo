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
import com.ricardo.appogeo.db.HistorialBusqueda
import com.ricardo.appogeo.db.RoomEntitie
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var historialBusqueda: RoomEntitie? = null
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val intent = this.intent
        if (intent != null) {

            textView_title!!.text = historialBusqueda!!.Title
            tv_streetAddress!!.text = historialBusqueda!!.Streetaddress
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (historialBusqueda != null && historialBusqueda!!.LAT != toString("0") && historialBusqueda!!.LON != 0.0) {
            val sydney = LatLng(historialBusqueda!!.LAT, historialBusqueda!!.LON)
            mMap!!.addMarker(MarkerOptions().position(sydney).title(historialBusqueda!!.Title))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            mMap!!.moveCamera(CameraUpdateFactory.zoomTo(17f))
        }
    }
}
