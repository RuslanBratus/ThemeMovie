package com.example.thememovie.main.viewmodel.repository.network

import com.example.thememovie.main.model.MovieParameters
import com.example.thememovie.openedmovie.model.FullMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("discover/movie")
    suspend fun getTopRatedMoviesPaged(
        @Query("api_key") accessKey: String,
        @Query("page") pageIndex: Int = 1,
        @Query("sort_by") sortParameter: String
        ): MovieParameters

    @GET("movie/{Id}")
    suspend fun getSoloMovie(
        @Path("Id") movieId: Long,
        @Query("api_key") accessKey: String,
    ): FullMovie
}