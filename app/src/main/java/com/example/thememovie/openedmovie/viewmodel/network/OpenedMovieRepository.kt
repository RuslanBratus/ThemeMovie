package com.example.thememovie.openedmovie.viewmodel.network

import com.example.thememovie.Utils
import com.example.thememovie.main.viewmodel.repository.network.MovieApiService
import javax.inject.Inject

class OpenedMovieRepository @Inject constructor(private val movieApiService: MovieApiService) {
    suspend fun getSoloMovieInfo(movieId: Long) = movieApiService.getSoloMovie(
        movieId = movieId,
        accessKey = Utils.ApiKey
    )
}