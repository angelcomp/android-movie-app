package com.angelica.myfavs.services

import com.angelica.myfavs.models.Description
import com.angelica.myfavs.models.ResultadoAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {

    private val service: Service
    private val API_KEY = "558b797e"

    init {
        val apiUrl = "http://www.omdbapi.com/"

        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        logger.redactHeader("Authorization")
        logger.redactHeader("Cookie")

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(Service::class.java)
    }

    suspend fun getLista(search: String, pagina: Int, tipo_pesquisa: String, anoLancamento: String ): ResultadoAPI? {
        return service.getBySearch(API_KEY, search, pagina, tipo_pesquisa, anoLancamento)
    }

    suspend fun getAllDescription(id: String): Description? {
        return service.getByID(API_KEY, id)
    }
}