package com.hellu.netflixcollection.ui.main.fragment.favorite.favmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.hellu.netflixcollection.data.MovieRepository
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity

class FavoriteMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getFavoriteMovies(sort: String): LiveData<PagedList<MovieEntity>> =
        movieRepository.getFavoriteMovies(sort)

    fun setFavoriteMovies(movieEntity: MovieEntity) {
        val newState = !movieEntity.favorite
        movieRepository.setMoviesFavorite(movieEntity, newState)
    }
}
