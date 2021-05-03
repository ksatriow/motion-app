package com.hellu.netflixcollection.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hellu.netflixcollection.data.MovieRepository
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.ui.detail.DetailsViewModel
import com.hellu.netflixcollection.utils.DataDummy
import com.hellu.netflixcollection.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var detailViewModel: DetailsViewModel

    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovie.id
    private val dummyTvShow = DataDummy.generateDummyTVShows()[0]
    private val tvShowId = dummyTvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieAppRepository: MovieRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<MovieEntity>>

    @Before
    fun setUp() {
        detailViewModel = DetailsViewModel(movieAppRepository)
        detailViewModel.selectedMovieId(movieId)
        detailViewModel.selectedTvShowId(tvShowId)
    }

    @Test
    fun getMovieDetail() {
        val movieDetail = Resource.success(dummyMovie)
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = movieDetail
        `when`(movieAppRepository.getDetailMovie(movieId)).thenReturn(movie)
        detailViewModel.movieDetail.observeForever(movieObserver)
        verify(movieObserver).onChanged(movieDetail)
    }

    @Test
    fun getTVShowDetail() {
        val tvShowDetail = Resource.success(dummyTvShow)
        val tvShow = MutableLiveData<Resource<MovieEntity>>()
        tvShow.value = tvShowDetail
        `when`(movieAppRepository.getDetailTVShow(tvShowId)).thenReturn(tvShow)
        detailViewModel.tvShowDetail.observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(tvShowDetail)
    }

    @Test
    fun setFavoriteMovie() {
        val dummyDetailMovie = Resource.success(DataDummy.generateDummyMovies()[0])
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        `when`(movieAppRepository.getDetailMovie(movieId)).thenReturn(movie)
        detailViewModel.movieDetail =  movieAppRepository.getDetailMovie(movieId)
        detailViewModel.setFavoriteMovie()
        com.nhaarman.mockitokotlin2.verify(movieAppRepository)
            .setMoviesFavorite(movie.value?.data as MovieEntity, true)
        verifyNoMoreInteractions(movieObserver)
    }

    @Test
    fun deleteFavoriteMovie() {
        val dummyDetailMovie = Resource.success(DataDummy.generateDummyMovies()[1])
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        `when`(movieAppRepository.getDetailMovie(movieId)).thenReturn(movie)
        detailViewModel.movieDetail =  movieAppRepository.getDetailMovie(movieId)
        detailViewModel.setFavoriteMovie()
        com.nhaarman.mockitokotlin2.verify(movieAppRepository)
            .setMoviesFavorite(movie.value?.data as MovieEntity, false)
        verifyNoMoreInteractions(movieObserver)
    }

    @Test
    fun setFavoriteTVShow() {
        val dummyDetailTV = Resource.success(DataDummy.generateDummyTVShows()[0])
        val tvShows = MutableLiveData<Resource<MovieEntity>>()
        tvShows.value = dummyDetailTV

        `when`(movieAppRepository.getDetailTVShow(tvShowId)).thenReturn(tvShows)
        detailViewModel.tvShowDetail =  movieAppRepository.getDetailTVShow(tvShowId)
        detailViewModel.setFavoriteTvShow()
        com.nhaarman.mockitokotlin2.verify(movieAppRepository)
            .setMoviesFavorite(tvShows.value?.data as MovieEntity, true)
        verifyNoMoreInteractions(tvShowObserver)
    }

    @Test
    fun deleteFavoriteTVShow() {
        val dummyDetailTV = Resource.success(DataDummy.generateDummyTVShows()[1])
        val tvShows = MutableLiveData<Resource<MovieEntity>>()
        tvShows.value = dummyDetailTV

        `when`(movieAppRepository.getDetailTVShow(tvShowId)).thenReturn(tvShows)
        detailViewModel.tvShowDetail =  movieAppRepository.getDetailTVShow(tvShowId)
        detailViewModel.setFavoriteTvShow()
        com.nhaarman.mockitokotlin2.verify(movieAppRepository)
            .setMoviesFavorite(tvShows.value?.data as MovieEntity, false)
        verifyNoMoreInteractions(tvShowObserver)
    }

}