package com.hellu.netflixcollection.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hellu.netflixcollection.data.source.remote.response.MovieResponse
import com.hellu.netflixcollection.data.source.remote.response.TVShowResponse
import com.hellu.netflixcollection.network.NetworkConfig
import com.hellu.netflixcollection.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getMovies(): LiveData<ApiResponse<List<Movie>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<Movie>>>()
        NetworkConfig.provideApiService().getMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                val results = response.body()?.results
                if (results != null) {
                    resultMovies.postValue(ApiResponse.success(results))
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(RemoteDataSource::class.simpleName, "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovies
    }

    fun getTVShow(): LiveData<ApiResponse<List<TVShow>>> {
        EspressoIdlingResource.increment()
        val resultTvShows = MutableLiveData<ApiResponse<List<TVShow>>>()
        NetworkConfig.provideApiService().getTVShows().enqueue(object : Callback<TVShowResponse> {
            override fun onResponse(
                call: Call<TVShowResponse>,
                response: Response<TVShowResponse>
            ) {
                val results = response.body()?.results
                if (results != null) {
                    resultTvShows.postValue(ApiResponse.success(results))
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
                Log.e(RemoteDataSource::class.simpleName, "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultTvShows
    }
}