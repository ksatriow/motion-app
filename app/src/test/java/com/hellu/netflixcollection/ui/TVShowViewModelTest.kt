package com.hellu.netflixcollection.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.hellu.netflixcollection.data.MovieRepository
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.ui.main.fragment.tvshow.TVShowsViewModel
import com.hellu.netflixcollection.utils.SortUtils
import com.hellu.netflixcollection.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TVShowViewModelTest {

    private lateinit var tvShowsViewModel: TVShowsViewModel
    private val sort = SortUtils.RANDOM

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieAppRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        tvShowsViewModel = TVShowsViewModel(movieAppRepository)
    }

    @Test
    fun getTVShows() {
        val dummyTvShows = Resource.success(pagedList)
        `when`(dummyTvShows.data?.size).thenReturn(5)
        val tvShows = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        tvShows.value = dummyTvShows

        `when`(movieAppRepository.getTVShow(sort)).thenReturn(tvShows)
        val tvShowEntities = tvShowsViewModel.getTvShows(sort).value?.data
        com.nhaarman.mockitokotlin2.verify(movieAppRepository).getTVShow(sort)
        assertNotNull(tvShowEntities)
        assertEquals(5, tvShowEntities?.size)

        tvShowsViewModel.getTvShows(sort).observeForever(observer)
        com.nhaarman.mockitokotlin2.verify(observer).onChanged(dummyTvShows)
    }

}