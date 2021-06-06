package com.angelica.myfavs.services

import com.angelica.myfavs.database.Favoritedb
import com.angelica.myfavs.models.Favorite

class FavoritesRepository(
    private val favoritodb: Favoritedb
) {

    fun getAll(): List<Favorite>? {
        return favoritodb.getAll()
    }

    fun getFavorite(id: String): Favorite? {
        return favoritodb.getFav(id)
    }

    suspend fun addFavorite(favorito: Favorite) {
        favoritodb.addFavorite(favorito)
    }

    suspend fun deleteFavorite(id: String) {
        favoritodb.deleteFavorite(id)
    }

    suspend fun deleteAllFavorites() {
        favoritodb.deleteAllFavorites()
    }
}