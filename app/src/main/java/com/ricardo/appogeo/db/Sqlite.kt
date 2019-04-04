package com.ricardo.appogeo.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.ricardo.appogeo.commons.HistorialBusqueda
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class Sqlite(ctx: Context) {

    var ctx: Context? = null
        private set
    private val db: SQLiteDatabase

    val historialBusquedas: ArrayList<HistorialBusqueda>
        get() {
            val result = ArrayList<HistorialBusqueda>()
            try {
                val cr = this.db.rawQuery("Select * from " + TableHistorial.TABLE_NAME + " ORDER BY " + TableHistorial.DATE + " DESC ", null)
                if (cr != null && cr.moveToNext()) {
                    do {
                        result.add(TableHistorial.getFromCursor(cr))
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

        val cr = this.db.rawQuery("Select * from " + TableHistorial.TABLE_NAME + " WHERE _id=" + historialBusqueda._id, null)
        if (cr != null && cr.moveToNext()) {
            val item = TableHistorial.getFromCursor(cr)
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
        values.put(TableHistorial._ID, search._id)
        values.put(TableHistorial.LAT, search.lat)
        values.put(TableHistorial.LON, search.lon)
        values.put(TableHistorial.DATE, this.getDateToSql(search.date))
        values.put(TableHistorial.Title, search.title)
        values.put(TableHistorial.Locality, search.locality)
        values.put(TableHistorial.Streetaddress, search.streetaddress)
        val newRowId = db.insert(TableHistorial.TABLE_NAME, null, values)
        search._id = newRowId.toInt()
    }

    private fun update(search: HistorialBusqueda) {
        val values = ContentValues()
        values.put(TableHistorial._ID, search._id)
        values.put(TableHistorial.LAT, search.lat)
        values.put(TableHistorial.LON, search.lon)
        values.put(TableHistorial.DATE, this.getDateToSql(search.date))
        values.put(TableHistorial.Title, search.title)
        values.put(TableHistorial.Locality, search.locality)
        values.put(TableHistorial.Streetaddress, search.streetaddress)
        val whereClause = "_id=" + search._id
        val newRowId = db.update(TableHistorial.TABLE_NAME, values, whereClause, null).toLong()
        search._id = newRowId.toInt()
    }

    companion object {

        val inst : AtomicReference<Sqlite> = AtomicReference()
        fun getInstance(ctx: Context): Sqlite {
            if (inst.get()==null) {
                inst.set(Sqlite(ctx))
            }
            return inst.get()
        }
    }
}
