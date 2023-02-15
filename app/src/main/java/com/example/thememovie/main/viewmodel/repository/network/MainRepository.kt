package com.example.thememovie.main.viewmodel.repository.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.thememovie.SortingParameters
import com.example.thememovie.Utils
import com.example.thememovie.main.model.MovieSimple
import javax.inject.Inject

class MainRepository @Inject constructor(private val movieApiService: MovieApiService):
    PagingSource<Int, MovieSimple>() {

    override fun getRefreshKey(state: PagingState<Int, MovieSimple>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSimple> {
        return try {
            Log.i("test", "Requesting Api for new default pack of images")
            val nextPageNumber = params.key ?: 1
            val response = movieApiService.getTopRatedMoviesPaged(
                Utils.ApiKey,
                pageIndex = nextPageNumber,
                sortParameter = SortingParameters.popularity)

            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.totalPages) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
