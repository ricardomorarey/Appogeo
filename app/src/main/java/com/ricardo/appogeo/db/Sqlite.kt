package com.ricardo.appogeo.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Sqlite {
    var instance: Sqlite? = null
    lateinit var table : Historial

    private var ctx: Context? = null
    private var db: SQLiteDatabase? = null

    fun EmbassiesSqlite(ctx: Context): Sqlite? {
        this.ctx = ctx
        this.db = SqliteHelper(this.ctx!!).getWritableDatabase()
        return instance
    }

    fun getCtx(): Context? {
        return ctx
    }

    private fun setCtx(ctx: Context) {
        this.ctx = ctx
    }

    fun getInstance(ctx: Context): Sqlite {
        if (instance == null) {
            instance = EmbassiesSqlite(ctx)
        }
        return instance as Sqlite
    }

    fun getLastSearchs(): ArrayList<HistorialBusqueda> {
        val result = ArrayList<HistorialBusqueda>()
        try {
            val cr = this.db!!.rawQuery(
                "Select * from " + table.TABLE_NAME + " ORDER BY " + table.DATE + " DESC ",
                null
            )
            if (cr != null && cr.moveToNext()) {
                do {
                    result.add(table.getFromCursor(cr))
                } while (cr.moveToNext())
                cr.close()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return result
    }

    fun upsert(lastSearch: HistorialBusqueda) {

        val cr =
            this.db!!.rawQuery("Select * from " + table.TABLE_NAME + " WHERE _id=" + lastSearch._id, null)
        if (cr != null && cr.moveToNext()) {
            val item = table.getFromCursor(cr)
            lastSearch._id
            this.update(lastSearch)
            cr.close()
        } else {
            this.insert(lastSearch)
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
        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(table.ID, search._id)

        values.put(table.LAT, search.lat)
        values.put(table.LON, search.lon)
        values.put(table.DATE, this.getDateToSql(search.date))

        values.put(table.Title, search.title)
        values.put(table.Locality, search.locality)
        values.put(table.Streetaddress, search.streetaddress)
        // Insert the new row, returning the primary key value of the new row
        val newRowId = db!!.insert(table.TABLE_NAME, null, values)
        search._id
    }

    private fun update(search: HistorialBusqueda) {
        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(table.ID, search._id)

        values.put(table.LAT, search.lat)
        values.put(table.LON, search.lon)
        values.put(table.DATE, this.getDateToSql(search.date))

        values.put(table.Title, search.title)
        values.put(table.Locality, search.locality)
        values.put(table.Streetaddress, search.streetaddress)

        // Insert the new row, returning the primary key value of the new row
        val whereClause = "_id=" + search._id
        val newRowId = db!!.update(table.TABLE_NAME, values, whereClause, null).toLong()
        search._id
    }
}