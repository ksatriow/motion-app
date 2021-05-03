package com.hellu.netflixcollection.di

import android.content.Context
import com.hellu.netflixcollection.data.MovieRepository
import com.hellu.netflixcollection.data.source.local.LocalDataSource
import com.hellu.netflixcollection.data.source.local.room.MovieDatabase
import com.hellu.netflixcollection.data.source.remote.RemoteDataSource
import com.hellu.netflixcollection.utils.AppExecutors

object Injection {
    fun dataRepository(context: Context): MovieRepository {
        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}