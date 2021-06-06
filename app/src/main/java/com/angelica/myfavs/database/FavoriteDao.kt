package com.angelica.myfavs.database

import androidx.room.*
import com.angelica.myfavs.models.Favorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    fun getAll(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorites WHERE id= :id")
    fun getFavorite(id: String): Flow<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites")
    suspend fun deleteAllFavorito()

    fun getFavoritoDistinct(id: String) = getFavorite(id).distinctUntilChanged()

}
