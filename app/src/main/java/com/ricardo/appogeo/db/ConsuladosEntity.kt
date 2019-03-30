package com.ricardo.appogeo.db

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class ConsuladosEntity (var c: String? = null,
                             var dcterms: String? = null,
                             var geo: String? = null,
                             var loc: String? = null,
                             var org: String? = null,
                             var vcard: String? = null,
                             var title: String? = null,
                             var id: String? = null,
                             var relation: String? = null,
                             var references: String? = null,
                             var address: String? = null,
                             var area: String? = null,
                             var district: String? = null,
                             var locality: String? = null,
                             var postalcode: String? = null,
                             var street: String? = null,
                             var location: String? = null,
                             var latitude: String? = null,
                             var longitude: String? = null,
                             var organization: String? = null,
                             var organizationdesc: String? = null,
                             var accesibility: String? = null,
                             var services: String? = null,
                             var schedule: String? = null,
                             var organizationname: String? = null,
                             var description: String? = null,
                             var link: String? = null,
                             var uid: String? = null,
                             var dtstart: String? = null,
                             var dtend: String? = null,
                             var excludeddays: String? = null,
                             var eventlocation: String? = null,
                             var price: String? = null,
                             var recurrence: String? = null,
                             var days: String? = null,
                             var frequency: String? = null,
                             var interval: String? = null,
                             var audience: String? = null) : Callback<ConsuladosEntity> {
    override fun onFailure(call: Call<ConsuladosEntity>, t: Throwable) {
    }

    override fun onResponse(call: Call<ConsuladosEntity>, response: Response<ConsuladosEntity>) {
    }
}

