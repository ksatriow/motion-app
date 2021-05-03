package com.hellu.netflixcollection.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hellu.netflixcollection.data.MovieRepository
import com.hellu.netflixcollection.di.Injection
import com.hellu.netflixcollection.ui.detail.DetailsViewModel
import com.hellu.netflixcollection.ui.main.fragment.favorite.favmovie.FavoriteMovieViewModel
import com.hellu.netflixcollection.ui.main.fragment.favorite.favtvshow.FavoriteTVShowViewModel
import com.hellu.netflixcollection.ui.main.fragment.movie.MoviesViewModel
import com.hellu.netflixcollection.ui.main.fragment.tvshow.TVShowsViewModel

class ViewModelFactory private constructor(private val mMovieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.dataRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                MoviesViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(TVShowsViewModel::class.java) -> {
                TVShowsViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                DetailsViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTVShowViewModel::class.java) -> {
                FavoriteTVShowViewModel(mMovieRepository) as T
            }
            else -> throw Throwable("Uknown viewModel class: ${modelClass.name}")
        }
    }
}