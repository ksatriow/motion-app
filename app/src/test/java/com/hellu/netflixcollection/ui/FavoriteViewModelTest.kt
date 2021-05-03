package com.hellu.netflixcollection.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.hellu.netflixcollection.data.MovieRepository
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.ui.main.fragment.favorite.favmovie.FavoriteMovieViewModel
import com.hellu.netflixcollection.ui.main.fragment.favorite.favtvshow.FavoriteTVShowViewModel
import com.hellu.netflixcollection.utils.DataDummy
import com.hellu.netflixcollection.utils.SortUtils
import com.hellu.netflixcollection.vo.Resource
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    private lateinit var movieViewModel: FavoriteMovieViewModel
    private lateinit var tvShowViewModel: FavoriteTVShowViewModel
    private val sort = SortUtils.RANDOM

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieAppRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        movieViewModel = FavoriteMovieViewModel(movieAppRepository)
        tvShowViewModel = FavoriteTVShowViewModel(movieAppRepository)
    }

    @Test
    fun getFavoriteMovies() {
        val dummyMovies = pagedList
        Mockito.`when`(dummyMovies.size).thenReturn(5)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovies

        Mockito.`when`(movieAppRepository.getFavoriteMovies(sort)).thenReturn(movies)
        val movieEntities = movieViewModel.getFavoriteMovies(sort).value
        Mockito.verify(movieAppRepository).getFavoriteMovies(sort)
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(5, movieEntities?.size)

        movieViewModel.getFavoriteMovies(sort).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getFavoriteTvShows() {
        val dummyTvShows = pagedList
        Mockito.`when`(dummyTvShows.size).thenReturn(5)
        val tvShows = MutableLiveData<PagedList<MovieEntity>>()
        tvShows.value = dummyTvShows

        Mockito.`when`(movieAppRepository.getFavoriteTVShows(sort)).thenReturn(tvShows)
        val tvShowEntities = tvShowViewModel.getFavoriteTVShows(sort).value
        Mockito.verify(movieAppRepository).getFavoriteTVShows(sort)
        Assert.assertNotNull(tvShowEntities)
        Assert.assertEquals(5, tvShowEntities?.size)

        tvShowViewModel.getFavoriteTVShows(sort).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShows)
    }

}