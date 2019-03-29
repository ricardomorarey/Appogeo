package com.ricardo.appogeo.db

data class ConsuladosEntity (val id: String,
                             val type : String,
                             val address: String,
                             val location: String,
                             val organization: String,
                             val relation: String,
                             val title: String,
                             val area: String,
                             val district: String,
                             val locality: String,
                             val postal_code: String,
                             val street_address: String,
                             val latitude: Double,
                             val longitude: Double,
                             val accesibility: String,
                             val organization_desc: String,
                             val organization_name: String,
                             val schedule: String,
                             val services: String)

