package com.hellu.netflixcollection.ui.main.fragment.favorite.favtvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.hellu.netflixcollection.data.MovieRepository
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity

class FavoriteTVShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getFavoriteTVShows(sort: String): LiveData<PagedList<MovieEntity>> =
        movieRepository.getFavoriteTVShows(sort)

    fun setFavoriteTVShows(movieEntity: MovieEntity) {
        val newState = !movieEntity.favorite
        movieRepository.setMoviesFavorite(movieEntity, newState)
    }
}

