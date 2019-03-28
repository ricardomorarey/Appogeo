package com.ricardo.appogeo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [HistorialEntity::class], version = 1)
public abstract class HistorialRoomDatabase : RoomDatabase(){
    abstract  fun historialDao(): AppogeoDao

    companion object {
        @Volatile
        private var INSTANCE: HistorialRoomDatabase? = null

        fun getDatabase(context: Context): HistorialRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistorialRoomDatabase::class.java,
                    "Word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}