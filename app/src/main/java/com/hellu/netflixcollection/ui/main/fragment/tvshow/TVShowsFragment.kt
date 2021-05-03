package com.hellu.netflixcollection.ui.main.fragment.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.hellu.netflixcollection.adapter.TVShowAdapter
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.databinding.FragmentTvshowsBinding
import com.hellu.netflixcollection.utils.SortUtils
import com.hellu.netflixcollection.utils.autoFitColumns
import com.hellu.netflixcollection.viewmodel.ViewModelFactory
import com.hellu.netflixcollection.vo.Resource
import com.hellu.netflixcollection.vo.Status

class TVShowsFragment : Fragment() {

    private var tvshowBinding: FragmentTvshowsBinding? = null
    private val binding get() = tvshowBinding!!

    private lateinit var tvShowsAdapter: TVShowAdapter
    private lateinit var viewModel: TVShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tvshowBinding = FragmentTvshowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[TVShowsViewModel::class.java]

        tvShowsAdapter = TVShowAdapter()
        setList(SortUtils.RANDOM)

        with(binding.rvTVShow) {
            autoFitColumns(180)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
        }

        binding.chipRandom.setOnClickListener { setList(SortUtils.RANDOM) }
        binding.chipNewest.setOnClickListener { setList(SortUtils.NEWEST) }
        binding.chipPopularity.setOnClickListener { setList(SortUtils.POPULARITY) }
        binding.chipVote.setOnClickListener { setList(SortUtils.VOTE) }

    }

    private fun setList(sort: String) {
        viewModel.getTvShows(sort).observe(this as LifecycleOwner, tvShowsObserver)
    }

    private val tvShowsObserver = Observer<Resource<PagedList<MovieEntity>>> { tvShow ->
        if (tvShow != null) {
            when (tvShow.status) {
                Status.LOADING -> {
                    binding.animationLoadingTV.visibility = View.VISIBLE
                    binding.notFound.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.animationLoadingTV.visibility = View.GONE
                    binding.notFound.visibility = View.GONE
                    tvShowsAdapter.notifyDataSetChanged()
                    tvShowsAdapter.submitList(tvShow.data)
                    tvShowsAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    binding.animationLoadingTV.visibility = View.GONE
                    binding.notFound.visibility = View.VISIBLE
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tvshowBinding = null
    }
}