package com.ricardo.appogeo.db

import android.app.Application
import android.os.AsyncTask


class HistorialRepository(application: Application) {

    private val historialDao: DaoHistorial? = HistorialRoomBataBase.getDatabase(application)?.daoHistorial()

    private val allH: List<RoomEntitie>? = null

    fun insert(historial: RoomEntitie) {
        if (historialDao != null) InsertAsyncTask(historialDao).execute(historial)
    }

    fun getHistorial(): List<RoomEntitie>? {
        return allH
    }

    private class InsertAsyncTask(private val historialDao: DaoHistorial) :
        AsyncTask<RoomEntitie, Void, Void>() {
        override fun doInBackground(vararg historials: RoomEntitie): Void? {
            for (historial in historials) {
                if (historial != null) historialDao.insert(historial)
            }
            return null
        }
    }
}