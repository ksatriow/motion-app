package com.hellu.netflixcollection.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.vo.Resource

interface MovieDataSource {
    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getTVShow(sort: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getDetailMovie(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getDetailTVShow(tvShowsId: Int): LiveData<Resource<MovieEntity>>

    fun getFavoriteMovies(sort: String): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTVShows(sort: String): LiveData<PagedList<MovieEntity>>

    fun setMoviesFavorite(movie: MovieEntity, state: Boolean)

}