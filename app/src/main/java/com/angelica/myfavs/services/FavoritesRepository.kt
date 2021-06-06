package com.angelica.myfavs.services

import com.angelica.myfavs.database.Favoritedb
import com.angelica.myfavs.models.Favorite
import com.angelica.myfavs.utils.ResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavoritesRepository(
    private val favoritodb: Favoritedb
) {

    fun getAll() = flow {
        var response = ResponseWrapper<List<Favorite>>(ResponseWrapper.Status.LOADING, null)
        emit(response)
        favoritodb.getAll()
            .catch { error ->
                response = ResponseWrapper(
                    ResponseWrapper.Status.ERROR,
                    null,
                    error.message
                )
                emit(response)
            }
            .collect { res ->
                response = if (res.isEmpty()) {
                    ResponseWrapper<List<Favorite>>(
                        ResponseWrapper.Status.ERROR,
                        null,
                        "Not found - empty list"
                    )
                } else {
                    ResponseWrapper<List<Favorite>>(ResponseWrapper.Status.SUCCESS, res)
                }
                emit(response)
            }
    }.flowOn(Dispatchers.Default)

    suspend fun addFavorito(favorito: Favorite) {
        favoritodb.addFavorite(favorito)
    }

    suspend fun deleteFavorito(favorito: Favorite) {
        favoritodb.deleteFavorite(favorito)
    }

}