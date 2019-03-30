package com.ricardo.appogeo.db

import android.database.Cursor
import java.text.SimpleDateFormat
import java.util.*

object Historial {

    val ID = "_id"
    val Streetaddress = "Streetaddress"
    val Locality = "Locality"
    val Title = "Title"
    val LON = "LON"
    val LAT = "LAT"
    val DATE = "DATE"
    val TABLE_NAME = "HistorialBusqueda"
    val CREATE_TABLE = "CREATE TABLE '" + TABLE_NAME + "' (" +
            "'_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            "'DATE' DATE," +
            "'Streetaddress' VARCHAR," +
            "'Locality' VARCHAR," +
            "'Title' VARCHAR," +
            "'LON' NUMERIC, " +
            "'LAT' NUMERIC" +
            ");"

    fun getFromCursor(cr: Cursor): HistorialBusqueda {
        val item = HistorialBusqueda()
        item._id = cr.getInt(0)
        item.date = getDateFromSql(cr.getString(1))
        item.streetaddress = cr.getString(2)
        item.locality = cr.getString(3)
        item.title = cr.getString(4)
        item.lon = cr.getDouble(5)
        item.lat = cr.getDouble(6)
        return item
    }

    fun getDateFromSql(date: String?): Date? {

        if (date == null || date.length == 0) { return null }
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            return sdf.parse(date)
        } catch (ex: Exception) { ex.printStackTrace() }
        return null
    }
}
