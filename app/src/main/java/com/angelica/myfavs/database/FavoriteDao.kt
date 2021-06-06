package com.angelica.myfavs.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.angelica.myfavs.models.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    fun getAll(): List<Favorite>?

    @Query("SELECT * FROM favorites WHERE id= :id")
    fun getFavorite(id: String): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteFavorite(id: String)

    @Query("DELETE FROM favorites")
    suspend fun deleteAllFavorito()
}
