package com.example.xplorer.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.xplorer.room.dao.CountryDao
import com.example.xplorer.room.dao.FavoriteDao
import com.example.xplorer.room.entities.Country
import com.example.xplorer.room.entities.Favorite

@Database(entities = [Country::class, Favorite::class], version = 3, exportSchema = false)
abstract class XplorerDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    abstract fun favoriteDao(): FavoriteDao

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