package com.angelica.myfavs.database

import com.angelica.myfavs.models.Favorite


//chamadas relacionadas a favoritos do repositório antigo ficam aqui
class Favoritedb(val favoritoDao: FavoriteDao) {

    suspend fun addFavorite(favorite: Favorite) = favoritoDao.addFavorite(favorite)

    fun getAll() = favoritoDao.getAll()

    suspend fun deleteFavorite(favorite: Favorite) = favoritoDao.deleteFavorite(favorite)

    suspend fun deleteAllFavorites() = favoritoDao.deleteAllFavorito()

}