package com.hellu.netflixcollection.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.hellu.netflixcollection.data.source.local.LocalDataSource
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.data.source.local.room.MovieDao
import com.hellu.netflixcollection.data.source.remote.RemoteDataSource
import com.hellu.netflixcollection.utils.*
import com.hellu.netflixcollection.vo.Resource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val movieAppRepository = FakeMovieRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateDummyRemoteMovies()
    private val movieId = movieResponses[0].id
    private val tvShowResponses = DataDummy.generateDummyRemoteTVShow()
    private val tvShowId = tvShowResponses[0].id

    private val random = SortUtils.RANDOM


    @Test
    fun getMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getAllMovies(random)).thenReturn(dataSourceFactory)
        movieAppRepository.getMovies(random)

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovies(random)
        Assert.assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getDetailMovie() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = DataDummy.generateDummyMovies()[0]
        Mockito.`when`(local.getMovie(movieId)).thenReturn(dummyMovie)

        val movieEntity = LiveDataTestUtil.getValue(movieAppRepository.getDetailMovie(movieId)).data
        verify(local).getMovie(movieId)
        val movieResponse = movieResponses[0]
        Assert.assertNotNull(movieEntity)
        if (movieEntity != null) {
            assertEquals(movieResponse.id, movieEntity.id)
            assertEquals(movieResponse.title, movieEntity.title)
            assertEquals(movieResponse.overview, movieEntity.overview)
            assertEquals(movieResponse.originalLanguage, movieEntity.originalLanguage)
            assertEquals(movieResponse.releaseDate, movieEntity.releaseDate)
            assertEquals(
                movieResponse.voteAverage,
                movieResponse.voteAverage,
                movieResponse.voteAverage
            )
            assertEquals(movieResponse.popularity, movieEntity.popularity, movieEntity.popularity)
            assertEquals(movieResponse.voteCount, movieEntity.voteCount)
            assertEquals(movieResponse.posterPath, movieEntity.posterPath)
        }
    }

    @Test
    fun getFavoriteMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getAllFavoriteMovies(random)).thenReturn(dataSourceFactory)
        movieAppRepository.getFavoriteMovies(random)

        val favoriteMovieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllFavoriteMovies(random)
        Assert.assertNotNull(favoriteMovieEntities)
        assertEquals(movieResponses.size.toLong(), favoriteMovieEntities.data?.size?.toLong())
    }

    @Test
    fun getTVShow() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getAllTvShows(random)).thenReturn(dataSourceFactory)
        movieAppRepository.getTVShow(random)

        val tvShowEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTVShows()))
        verify(local).getAllTvShows(random)
        Assert.assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getDetailTVShow() {
        val dummyTvShow = MutableLiveData<MovieEntity>()
        dummyTvShow.value = DataDummy.generateDummyTVShows()[0]
        Mockito.`when`(local.getTvShow(tvShowId)).thenReturn(dummyTvShow)

        val tvShowEntity =
            LiveDataTestUtil.getValue(movieAppRepository.getDetailTVShow(tvShowId)).data
        verify(local).getTvShow(tvShowId)
        val tvShowResponse = tvShowResponses[0]
        Assert.assertNotNull(tvShowEntity)
        if (tvShowEntity != null) {
            assertEquals(tvShowResponse.id, tvShowEntity.id)
            assertEquals(tvShowResponse.name, tvShowEntity.title)
            assertEquals(tvShowResponse.overview, tvShowEntity.overview)
            assertEquals(tvShowResponse.originalLanguage, tvShowEntity.originalLanguage)
            assertEquals(tvShowResponse.firstAirDate, tvShowEntity.releaseDate)
            assertEquals(
                tvShowResponse.voteAverage,
                tvShowResponse.voteAverage,
                tvShowResponse.voteAverage
            )
            assertEquals(
                tvShowResponse.popularity,
                tvShowEntity.popularity,
                tvShowEntity.popularity
            )
            assertEquals(tvShowResponse.voteCount, tvShowEntity.voteCount)
            assertEquals(tvShowResponse.posterPath, tvShowEntity.posterPath)
        }
    }

    @Test
    fun getFavoriteTvShows() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getAllFavoriteTvShows(random)).thenReturn(dataSourceFactory)
        movieAppRepository.getFavoriteTVShows(random)

        val favoriteTVShowEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTVShows()))
        verify(local).getAllFavoriteTvShows(random)
        Assert.assertNotNull(favoriteTVShowEntities)
        assertEquals(tvShowResponses.size.toLong(), favoriteTVShowEntities.data?.size?.toLong())
    }

//    @Test
//    fun getBookmarkedCourses() {
//        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
//        `when`(local.getAllFavoriteMovies(random)).thenReturn(dataSourceFactory)
//        movieAppRepository.getFavoriteMovies(random)
//
//        val courseEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
//        verify(local).getAllFavoriteMovies(random)
//        assertNotNull(courseEntities)
//        assertEquals(movieResponses.size.toLong(), courseEntities.data?.size?.toLong())
//    }


}