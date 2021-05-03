package com.hellu.netflixcollection.ui.main.fragment.favorite.favtvshow

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
import com.hellu.netflixcollection.adapter.TVShowAdapter
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.databinding.FragmentFavoriteTVShowBinding
import com.hellu.netflixcollection.utils.SortUtils
import com.hellu.netflixcollection.utils.autoFitColumns
import com.hellu.netflixcollection.viewmodel.ViewModelFactory

class FavoriteTVShowFragment : Fragment() {

    private var _fragmentFavoriteTvShowsBinding: FragmentFavoriteTVShowBinding? = null
    private val binding get() = _fragmentFavoriteTvShowsBinding!!

    private lateinit var tvShowsAdapter: TVShowAdapter
    private lateinit var viewModel: FavoriteTVShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentFavoriteTvShowsBinding =
            FragmentFavoriteTVShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding.rvFavoriteTVShow)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavoriteTVShowViewModel::class.java]

        tvShowsAdapter = TVShowAdapter()

        binding.animationLoadingTV.visibility = View.VISIBLE
        binding.notFound.visibility = View.GONE
        setList(SortUtils.RANDOM)

        with(binding.rvFavoriteTVShow) {
            autoFitColumns(180)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
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
                val tvShowEntity = tvShowsAdapter.getSwipedData(swipedPosition)
                tvShowEntity?.let { viewModel.setFavoriteTVShows(it) }
                val snackbar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { _ ->
                    tvShowEntity?.let { viewModel.setFavoriteTVShows(it) }
                }
                snackbar.show()
            }
        }
    })

    private fun setList(sort: String) {
        viewModel.getFavoriteTVShows(sort).observe(this as LifecycleOwner, tvShowsObserver)
    }

    private val tvShowsObserver = Observer<PagedList<MovieEntity>> { tvShows ->
        if (tvShows.isNotEmpty()) {
            binding.animationLoadingTV.visibility = View.GONE
            binding.notFound.visibility = View.GONE
            binding.tvTVNotFound.visibility = View.GONE
            tvShowsAdapter.submitList(tvShows)
            tvShowsAdapter.notifyDataSetChanged()
        } else {
            binding.animationLoadingTV.visibility = View.GONE
            binding.notFound.visibility = View.VISIBLE
            binding.tvTVNotFound.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentFavoriteTvShowsBinding = null
    }

}