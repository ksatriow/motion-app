package com.hellu.netflixcollection.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hellu.netflixcollection.data.source.local.LocalDataSource
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.data.source.remote.ApiResponse
import com.hellu.netflixcollection.data.source.remote.Movie
import com.hellu.netflixcollection.data.source.remote.RemoteDataSource
import com.hellu.netflixcollection.data.source.remote.TVShow
import com.hellu.netflixcollection.utils.AppExecutors
import com.hellu.netflixcollection.vo.Resource

class FakeMovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MovieDataSource {
    override fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<Movie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<Movie>>> {
                return remoteDataSource.getMovies()
            }

            override fun saveCallResult(data: List<Movie>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        response.overview,
                        response.originalLanguage,
                        response.releaseDate,
                        response.popularity,
                        response.voteAverage,
                        response.id,
                        response.title,
                        response.voteCount,
                        response.posterPath,
                        favorite = false,
                        isTvShows = false
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getTVShow(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<TVShow>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShows(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<TVShow>>> {
                return remoteDataSource.getTVShow()
            }

            override fun saveCallResult(data: List<TVShow>) {
                val tvShowList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        response.overview,
                        response.originalLanguage,
                        response.firstAirDate,
                        response.popularity,
                        response.voteAverage,
                        response.id,
                        response.name,
                        response.voteCount,
                        response.posterPath,
                        favorite = false,
                        isTvShows = true
                    )
                    tvShowList.add(movie)
                }
                localDataSource.insertMovies(tvShowList)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, List<Movie>>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getMovie(movieId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<List<Movie>>> {
                return remoteDataSource.getMovies()
            }

            override fun saveCallResult(data: List<Movie>) {
                lateinit var movieEntity: MovieEntity
                for (movie in data) {
                    if (movieId == movie.id) {
                        movieEntity = MovieEntity(
                            movie.overview,
                            movie.originalLanguage,
                            movie.releaseDate,
                            movie.popularity,
                            movie.voteAverage,
                            movie.id,
                            movie.title,
                            movie.voteCount,
                            movie.posterPath,
                            favorite = false,
                            isTvShows = false
                        )
                    }
                }
                localDataSource.updateMovie(movieEntity)
            }
        }.asLiveData()
    }

    override fun getDetailTVShow(tvShowsId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, List<TVShow>>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getTvShow(tvShowsId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<List<TVShow>>> {
                return remoteDataSource.getTVShow()
            }

            override fun saveCallResult(data: List<TVShow>) {
                lateinit var movieEntity: MovieEntity
                for (movie in data) {
                    if (tvShowsId == movie.id) {
                        movieEntity = MovieEntity(
                            movie.overview,
                            movie.originalLanguage,
                            movie.firstAirDate,
                            movie.popularity,
                            movie.voteAverage,
                            movie.id,
                            movie.name,
                            movie.voteCount,
                            movie.posterPath,
                            favorite = false,
                            isTvShows = true
                        )
                    }
                }
                localDataSource.updateMovie(movieEntity)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(sort: String): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getAllFavoriteMovies(sort), config).build()
    }

    override fun getFavoriteTVShows(sort: String): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getAllFavoriteTvShows(sort), config).build()
    }

    override fun setMoviesFavorite(movie: MovieEntity, state: Boolean) {
        return appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movie, state) }
    }

}
