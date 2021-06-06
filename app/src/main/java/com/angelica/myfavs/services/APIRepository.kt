package com.angelica.myfavs.services

import com.angelica.myfavs.database.Favoritedb
import com.angelica.myfavs.models.Description
import com.angelica.myfavs.models.ResultadoAPI

class APIRepository(
    private val serviceAPI: ServiceAPI, val favoritedb: Favoritedb) {

    private val API_KEY = "558b797e"

    suspend fun getLista(search: String, pagina: Int, tipo_pesquisa: String, anoLancamento: String ): ResultadoAPI? {
        return serviceAPI.getBySearch(API_KEY, search, pagina, tipo_pesquisa, anoLancamento)
    }

    suspend fun getAllDescription(id: String): Description? {
        return serviceAPI.getByID(API_KEY, id)
    }
}