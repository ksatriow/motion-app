package com.hellu.netflixcollection.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.hellu.netflixcollection.BuildConfig
import com.hellu.netflixcollection.R
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.databinding.ActivityDetailsBinding
import com.hellu.netflixcollection.utils.show
import com.hellu.netflixcollection.viewmodel.ViewModelFactory
import com.hellu.netflixcollection.vo.Resource
import com.hellu.netflixcollection.vo.Status
import kotlinx.android.synthetic.main.child_tolbar.*

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
    }

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getIntExtra(EXTRA_TYPE, -1)
        val enumType: DetailType = DetailType.values()[type]

        val id = intent.getIntExtra(EXTRA_ID, -1)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailsViewModel::class.java]

        btn_back.setOnClickListener { onBackPressed() }

        binding.animationLoading.visibility = View.VISIBLE
        binding.backgroundLoading.visibility = View.GONE
        when (enumType) {
            DetailType.MOVIE -> {
                viewModel.selectedMovieId(id)
                viewModel.movieDetail.observe(this, { movie ->
                    if (movie != null) {
                        visibilityStateDetail(movie)
                    }
                })
            }
            DetailType.TVSHOW -> {
                viewModel.selectedTvShowId(id)
                viewModel.tvShowDetail.observe(this, { tvShow ->
                    if (tvShow != null) {
                        visibilityStateDetail(tvShow)
                    }
                })
            }
        }

        binding.favoriteButton.setOnClickListener {
            when (enumType) {
                DetailType.MOVIE -> {
                    viewModel.setFavoriteMovie()
                }
                DetailType.TVSHOW -> {
                    viewModel.setFavoriteTvShow()
                }
            }
        }
    }

    private fun visibilityStateDetail(movie: Resource<MovieEntity>) {
        when (movie.status) {
            Status.LOADING -> binding.animationLoading.visibility = View.VISIBLE
            Status.SUCCESS -> if (movie.data != null) {
                binding.animationLoading.visibility = View.GONE
                binding.backgroundLoading.visibility = View.VISIBLE

                val state = movie.data.favorite

                setFavoriteState(state)
                populateDetail(movie.data)
            }
            Status.ERROR -> {
                binding.animationLoading.visibility = View.GONE
                Toast.makeText(
                    this,
                    "Terjadi kesalahan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
        } else {
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_border
                )
            )
        }
    }

    private fun populateDetail(movieEntity: MovieEntity) {
        with(binding) {
            tv_detail_title.text = movieEntity.title
            titleMovieDesc.text = movieEntity.title
            textOverview.text = movieEntity.overview
            tvValuePopular.text = movieEntity.popularity.toString()
            tvValueRating.text = movieEntity.voteAverage.toString()
            detailImage.show("${BuildConfig.IMG_URL}${movieEntity.posterPath}")
        }
    }
}