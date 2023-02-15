package com.example.thememovie.main.model

import com.google.gson.annotations.SerializedName


data class MovieParameters(
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val results: List<MovieSimple>
)

data class MovieSimple(
    val adult: Boolean,
    val id: Long,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("poster_path") val posterPath: String
)
