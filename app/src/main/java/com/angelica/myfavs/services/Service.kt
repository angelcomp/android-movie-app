package com.angelica.myfavs.services

import com.angelica.myfavs.models.Description
import com.angelica.myfavs.models.ResultadoAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("?")
    fun getBySearch(
        @Query("apikey") apikey: String,
        @Query("s") search: String,
        @Query("page") page: Int,
        @Query("type") type: String,
        @Query("y") anoLancamento: String
    ): Call<ResultadoAPI>

    @GET("?")
    fun getByID(
        @Query("apikey") apikey: String,
        @Query("i") ID: String,
        @Query("plot") plot: String = "full"
    ): Call<Description>
}