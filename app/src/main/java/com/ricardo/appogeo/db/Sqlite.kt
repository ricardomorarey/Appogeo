package com.ricardo.appogeo.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.text.SimpleDateFormat
import java.util.*

class Sqlite(ctx: Context) {

    var ctx: Context? = null
        private set
    private val db: SQLiteDatabase

    val historialBusquedas: ArrayList<HistorialBusqueda>
        get() {
            val result = ArrayList<HistorialBusqueda>()
            try {
                val cr = this.db.rawQuery("Select * from " + Historial.TABLE_NAME + " ORDER BY " + Historial.DATE + " DESC ", null)
                if (cr != null && cr.moveToNext()) {
                    do {
                        result.add(Historial.getFromCursor(cr))
                    } while (cr.moveToNext())
                    cr.close()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return result
        }

    init {
        this.ctx = ctx
        this.db = SqliteHelper(this.ctx!!).writableDatabase
    }

    fun upsert(historialBusqueda: HistorialBusqueda) {

        val cr = this.db.rawQuery("Select * from " + Historial.TABLE_NAME + " WHERE _id=" + historialBusqueda._id, null)
        if (cr != null && cr.moveToNext()) {
            val item = Historial.getFromCursor(cr)
            historialBusqueda._id = item._id
            this.update(historialBusqueda)
            cr.close()
        } else {
            this.insert(historialBusqueda)
        }
    }

    private fun getDateFromSql(date: String?): Date? {

        if (date == null || date.length == 0) {
            return null
        }
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            return sdf.parse(date)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    private fun getDateToSql(date: Date?): String {
        if (date == null) {
            return ""
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        return sdf.format(date)
    }

    private fun insert(search: HistorialBusqueda) {
        val values = ContentValues()
        values.put(Historial.ID, search._id)
        values.put(Historial.LAT, search.lat)
        values.put(Historial.LON, search.lon)
        values.put(Historial.DATE, this.getDateToSql(search.date))
        values.put(Historial.Title, search.title)
        values.put(Historial.Locality, search.locality)
        values.put(Historial.Streetaddress, search.streetaddress)
        val newRowId = db.insert(Historial.TABLE_NAME, null, values)
        search._id = newRowId.toInt()
    }

    private fun update(search: HistorialBusqueda) {
        val values = ContentValues()
        values.put(Historial.ID, search._id)
        values.put(Historial.LAT, search.lat)
        values.put(Historial.LON, search.lon)
        values.put(Historial.DATE, this.getDateToSql(search.date))
        values.put(Historial.Title, search.title)
        values.put(Historial.Locality, search.locality)
        values.put(Historial.Streetaddress, search.streetaddress)
        val whereClause = "_id=" + search._id
        val newRowId = db.update(Historial.TABLE_NAME, values, whereClause, null).toLong()
        search._id = newRowId.toInt()
    }

    companion object {
        var instance: Sqlite? = null
        fun getInstance(ctx: Context): Sqlite {
            if (instance == null) {
                instance = Sqlite(ctx)
            }
            return instance as Sqlite
        }
    }
}
