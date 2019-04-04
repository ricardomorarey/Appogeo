package com.ricardo.appogeo.commons

import java.io.Serializable
import java.util.Date

class HistorialBusqueda : Serializable {
    var _id: Int = 0
    var date: Date? = null
    var lon: Double = 0.toDouble()
    var lat: Double = 0.toDouble()
    var streetaddress: String? = null
    var locality: String? = null
    var title: String? = null
}
