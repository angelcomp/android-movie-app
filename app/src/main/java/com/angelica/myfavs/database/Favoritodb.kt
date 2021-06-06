package com.angelica.myfavs.database

import com.angelica.myfavs.models.Favorite


class Favoritedb(val favoritoDao: FavoriteDao) {

    suspend fun addFavorite(favorite: Favorite) = favoritoDao.addFavorite(favorite)

    fun getFav(id: String) = favoritoDao.getFavorite(id)

    suspend fun deleteFavorite(id: String) = favoritoDao.deleteFavorite(id)

    fun getAll() = favoritoDao.getAll()

    suspend fun deleteAllFavorites() = favoritoDao.deleteAllFavorito()

}