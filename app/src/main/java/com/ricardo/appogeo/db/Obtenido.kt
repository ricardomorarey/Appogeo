package com.ricardo.appogeo.db

import com.google.gson.annotations.SerializedName

class Obtenido {

    @SerializedName("address")
    lateinit var address: Address
    @SerializedName("location")
    lateinit var location: Location
    @SerializedName("organization")
    lateinit var organization: Organization
    @SerializedName("@id")
    internal var _id: String? = null
    var id: String? = null
    var relation: String? = null
    var title: String? = null
    @SerializedName("@type")
    internal lateinit var _type: String

    fun getid(): String? { return id }
    fun gettype(): String { return _type }
    fun setid(id: String) { this.id = id }
    fun settype(type: String) { this._type = type }

    inner class Address {
        @SerializedName("area")
        lateinit var area: Area
        @SerializedName("district")
        lateinit var district: District
        var locality: String? = null
        @SerializedName("postal-code")
        var postalcode: String? = null
        @SerializedName("street-address")
        var streetaddress: String? = null
    }

    inner class Area {
        @SerializedName("@id")
        internal lateinit var id: String

        fun getid(): String { return id }
        fun setid(id: String) { this.id = id }
    }

    inner class District {
        @SerializedName("@id")
        internal lateinit var id: String

        fun getid(): String { return id }
        fun setid(id: String) { this.id = id }
    }

    inner class Location {
        var latitude: Float = 0.toFloat()
        var longitude: Float = 0.toFloat()
    }

    inner class Organization {
        var accesibility: String? = null
        var organizationdesc: String? = null
        var organizationname: String? = null
        var schedule: String? = null
        var services: String? = null
    }
}
