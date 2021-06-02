package com.angelica.myfavs.services

import com.angelica.myfavs.models.ResultadoAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {

    private val service: Service

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

    fun getLista(search: String, pagina: Int, tipo_pesquisa: String): ResultadoAPI? {

        val call = service.get("558b797e", search, pagina, tipo_pesquisa)

        return call.execute().body()
    }
}