package com.ricardo.appogeo.db

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class HistorialBusqueda() : Parcelable {
    var _id: Int = 0
    var date: Date? = null
    var lon: Double = 0.toDouble()
    var lat: Double = 0.toDouble()
    var streetaddress: String? = null
    var locality: String? = null
    var title: String? = null

    constructor(parcel: Parcel) : this() {
        _id = parcel.readInt()
        lon = parcel.readDouble()
        lat = parcel.readDouble()
        streetaddress = parcel.readString()
        locality = parcel.readString()
        title = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(_id)
        parcel.writeDouble(lon)
        parcel.writeDouble(lat)
        parcel.writeString(streetaddress)
        parcel.writeString(locality)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HistorialBusqueda> {
        override fun createFromParcel(parcel: Parcel): HistorialBusqueda {
            return HistorialBusqueda(parcel)
        }

        override fun newArray(size: Int): Array<HistorialBusqueda?> {
            return arrayOfNulls(size)
        }
    }
}