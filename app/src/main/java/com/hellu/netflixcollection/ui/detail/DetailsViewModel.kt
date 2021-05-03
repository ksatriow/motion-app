package com.hellu.netflixcollection.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hellu.netflixcollection.data.MovieRepository
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.vo.Resource

class DetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val movieId = MutableLiveData<Int>()
    private val tvShowId = MutableLiveData<Int>()

    fun selectedMovieId(movieId: Int) {
        this.movieId.value = movieId
    }

    fun selectedTvShowId(tvShowId: Int) {
        this.tvShowId.value = tvShowId
    }

    var movieDetail: LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(movieId) { mMovieId ->
            movieRepository.getDetailMovie(mMovieId)
        }

    var tvShowDetail: LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(tvShowId) { mTvShowId ->
            movieRepository.getDetailTVShow(mTvShowId)
        }

    fun setFavoriteMovie() {
        val movieResource = movieDetail.value?.data
        if (movieResource != null) {
            val newState = !movieResource.favorite
            movieRepository.setMoviesFavorite(movieResource, newState)
        }
    }

    fun setFavoriteTvShow() {
        val tvShowResource = tvShowDetail.value?.data
        if (tvShowResource != null) {
            val newState = !tvShowResource.favorite
            movieRepository.setMoviesFavorite(tvShowResource, newState)
        }
    }
}