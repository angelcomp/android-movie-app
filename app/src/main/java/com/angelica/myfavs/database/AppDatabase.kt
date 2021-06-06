package com.angelica.myfavs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.angelica.myfavs.models.Favorite

@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritosDao(): FavoriteDao
}