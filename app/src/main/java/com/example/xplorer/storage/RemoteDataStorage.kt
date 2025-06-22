package com.example.xplorer.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.xplorer.room.dao.CountryDao
import com.example.xplorer.room.entities.Country

@Database(entities = [Country::class], version = 4, exportSchema = false)
abstract class XplorerDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var INSTANCE: XplorerDatabase? = null

        fun getDatabase(context: Context): XplorerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    XplorerDatabase::class.java,
                    "xplorer_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}