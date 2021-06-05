package com.angelica.myfavs.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultadoAPI(
    @SerializedName("Error") val error: String,
    @SerializedName("Search") val searches : List<Search>,
    val totalResults : Int,
    @SerializedName("Response") val response : Boolean
)

data class Search (
    @SerializedName("Title") val title : String,
    @SerializedName("Year") val year : String,
    val imdbID : String,
    @SerializedName("Type") val type : String,
    @SerializedName("Poster") val poster : String
): Serializable