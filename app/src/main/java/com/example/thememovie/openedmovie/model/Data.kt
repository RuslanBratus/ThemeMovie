package com.example.thememovie.openedmovie.model

import com.google.gson.annotations.SerializedName

data class FullMovie(
    val overview: String,
    val releaseDate: String,
    val adult: Boolean,
    val id: Int,
    val genres: List<Genres>,
    @SerializedName("vote_average") val rate: Float,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("poster_path") val posterPath: String
) {
    fun genresToString(): String {
        val genresTxt = java.lang.StringBuilder()
        genres.forEachIndexed { index, genre ->
            genresTxt.append(genre.name)
            if (index < genres.size-1)
                genresTxt.append(", ")
        }
        return genresTxt.toString()
    }
}

data class Genres(
    val name: String
)