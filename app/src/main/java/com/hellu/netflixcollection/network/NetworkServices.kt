package com.hellu.netflixcollection.network

import com.hellu.netflixcollection.BuildConfig
import com.hellu.netflixcollection.data.source.remote.response.MovieResponse
import com.hellu.netflixcollection.data.source.remote.response.TVShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkServices {

    @GET("movie?")
    fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.MOVIE_API
    ): Call<MovieResponse>

    @GET("tv?")
    fun getTVShows(
        @Query("api_key") apiKey: String = BuildConfig.MOVIE_API
    ): Call<TVShowResponse>

}