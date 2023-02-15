package com.example.thememovie.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.thememovie.main.viewmodel.repository.network.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {
    val images =
        Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory = {
            mainRepository
        }).flow.cachedIn(viewModelScope)
}