package com.angelica.myfavs.services

import android.util.Log
import com.angelica.myfavs.models.ResultadoAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    fun lista(search: String): ResultadoAPI? {

        val call = service.get("558b797e", search)

        return call.execute().body()
    }
}