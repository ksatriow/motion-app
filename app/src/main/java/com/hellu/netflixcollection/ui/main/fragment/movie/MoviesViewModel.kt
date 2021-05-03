package com.hellu.netflixcollection.ui.main.fragment.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.hellu.netflixcollection.data.MovieRepository
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.vo.Resource

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> =
        movieRepository.getMovies(sort)

}