package com.angelica.myfavs.models

import com.google.gson.annotations.SerializedName

data class ResultadoAPI(
    @SerializedName("Search") val search : List<Info>,
    val totalResults : Int,
    @SerializedName("Response") val response : Boolean
)

data class Info (
    @SerializedName("Title") val title : String,
    @SerializedName("Year") val year : String,
    val imdbID : String,
    @SerializedName("Type") val type : String,
    @SerializedName("Poster") val poster : String
)