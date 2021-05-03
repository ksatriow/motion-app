package com.hellu.netflixcollection.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.hellu.netflixcollection.R
import com.hellu.netflixcollection.databinding.ActivityMainBinding
import com.hellu.netflixcollection.ui.main.fragment.favorite.FavoriteFragment
import com.hellu.netflixcollection.ui.main.fragment.movie.MoviesFragment
import com.hellu.netflixcollection.ui.main.fragment.tvshow.TVShowsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityHomeBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        navigationChange(MoviesFragment())
        activityHomeBinding.topNavigationContainer.setNavigationChangeListener { _, position ->
            when (position) {
                0 -> navigationChange(MoviesFragment())
                1 -> navigationChange(TVShowsFragment())
                2 -> navigationChange(FavoriteFragment())
            }
        }
        supportActionBar?.elevation = 0f
    }

    private fun navigationChange(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}