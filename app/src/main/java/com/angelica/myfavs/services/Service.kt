package com.angelica.myfavs.services

import com.angelica.myfavs.models.ResultadoAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("?")
    fun get(
        @Query("apikey") apikey: String,
        @Query("s") search: String,
        @Query("page") page: Int,
        @Query("type") type: String
    ): Call<ResultadoAPI>

}