package com.ricardo.appogeo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RoomEntitie::class], version = 1)
abstract class HistorialRoomBataBase : RoomDatabase(){

    abstract fun daoHistorial(): DaoHistorial

    companion object {
        @Volatile
        private var INSTANCE: HistorialRoomBataBase? = null

        fun getDatabase (context: Context): HistorialRoomBataBase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistorialRoomBataBase::class.java,
                    "SqliteHelper"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}