package com.hellu.netflixcollection.ui.main.fragment.favorite.favmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hellu.netflixcollection.R
import com.hellu.netflixcollection.adapter.MovieAdapter
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.databinding.FragmentFavoriteMovieBinding
import com.hellu.netflixcollection.utils.SortUtils
import com.hellu.netflixcollection.utils.autoFitColumns
import com.hellu.netflixcollection.viewmodel.ViewModelFactory


class FavoriteMovieFragment : Fragment() {

    private var _fragmentFavoriteMoviesBinding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _fragmentFavoriteMoviesBinding!!

    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var viewModel: FavoriteMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentFavoriteMoviesBinding =
            FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding.rvFavoriteMovie)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

        moviesAdapter = MovieAdapter()

        binding.animationLoadingTV.visibility = View.VISIBLE
        binding.notFound.visibility = View.GONE
        setList(SortUtils.RANDOM)

        with(binding.rvFavoriteMovie) {
            autoFitColumns(180)
            setHasFixedSize(true)
            this.adapter = moviesAdapter
        }

    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = moviesAdapter.getSwipedData(swipedPosition)
                movieEntity?.let { viewModel.setFavoriteMovies(it) }
                val snackbar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { _ ->
                    movieEntity?.let { viewModel.setFavoriteMovies(it) }
                }
                snackbar.show()
            }
        }
    })

    private fun setList(sort: String) {
        viewModel.getFavoriteMovies(sort).observe(this as LifecycleOwner, moviesObserver)
    }

    private val moviesObserver = Observer<PagedList<MovieEntity>> { movies ->
        if (movies.isNotEmpty()) {
            binding.animationLoadingTV.visibility = View.GONE
            binding.notFound.visibility = View.GONE
            binding.tvMovieNotFound.visibility = View.GONE
            moviesAdapter.submitList(movies)
            moviesAdapter.notifyDataSetChanged()
        } else {
            binding.animationLoadingTV.visibility = View.GONE
            binding.notFound.visibility = View.VISIBLE
            binding.tvMovieNotFound.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentFavoriteMoviesBinding = null
    }

}