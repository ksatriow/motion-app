package com.hellu.netflixcollection.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hellu.netflixcollection.BuildConfig
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.databinding.ItemMovieBinding
import com.hellu.netflixcollection.ui.detail.DetailType
import com.hellu.netflixcollection.ui.detail.DetailsActivity
import com.hellu.netflixcollection.utils.show

class TVShowAdapter :
    PagedListAdapter<MovieEntity, TVShowAdapter.TvShowsViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {
        val itemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowsViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: TVShowAdapter.TvShowsViewHolder, position: Int) {
        val tvShowEntity = getItem(position)
        if (tvShowEntity != null) {
            holder.bind(tvShowEntity)
        }
    }

    inner class TvShowsViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieEntity: MovieEntity) {
            with(binding) {
                releasedDate.text = movieEntity.releaseDate
                voteAverage.text = movieEntity.voteAverage.toString()
                movieImage.show("${BuildConfig.IMG_URL}${movieEntity.posterPath}")
                movieName.text = movieEntity.title
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailsActivity::class.java)
                    intent.putExtra(DetailsActivity.EXTRA_TYPE, DetailType.TVSHOW.ordinal)
                    intent.putExtra(DetailsActivity.EXTRA_ID, movieEntity.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    fun getSwipedData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)
}