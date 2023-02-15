package com.example.thememovie.openedmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thememovie.main.viewmodel.repository.network.MainRepository
import com.example.thememovie.openedmovie.model.FullMovie
import com.example.thememovie.openedmovie.viewmodel.network.OpenedMovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class OpenedMovieViewModel @Inject constructor(
    private val repository: OpenedMovieRepository
    ): ViewModel() {

    val image = MutableLiveData<FullMovie>()

    fun requestSoloMovieInfo(movieId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            image.postValue(repository.getSoloMovieInfo(movieId = movieId))
        }
    }

}