package com.hellu.netflixcollection.ui.main.fragment.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.hellu.netflixcollection.data.MovieRepository
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.vo.Resource

class TVShowsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getTvShows(sort: String): LiveData<Resource<PagedList<MovieEntity>>> =
        movieRepository.getTVShow(sort)

}