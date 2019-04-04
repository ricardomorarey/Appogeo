package com.ricardo.appogeo.commons

import com.google.android.gms.maps.model.LatLng

class ConsuladoLAT : ConsuladosEntity() {
    fun getLATLon():LatLng{
        return LatLng(this.latitude!!.toDouble(),this.longitude!!.toDouble())
    }
}