package com.hellu.netflixcollection.ui.main.fragment.movie

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
import com.hellu.netflixcollection.adapter.MovieAdapter
import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.databinding.FragmentMoviesBinding
import com.hellu.netflixcollection.utils.SortUtils
import com.hellu.netflixcollection.utils.autoFitColumns
import com.hellu.netflixcollection.viewmodel.ViewModelFactory
import com.hellu.netflixcollection.vo.Resource
import com.hellu.netflixcollection.vo.Status

class MoviesFragment : Fragment() {

    private var _fragmentMoviesBinding: FragmentMoviesBinding? = null
    private val binding get() = _fragmentMoviesBinding!!

    private lateinit var viewModel: MoviesViewModel

    private lateinit var moviesAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]

        moviesAdapter = MovieAdapter()
        setList(SortUtils.RANDOM)

        with(binding.rvMovies) {
            autoFitColumns(180)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }

        binding.chipRandom.setOnClickListener { setList(SortUtils.RANDOM) }
        binding.chipNewest.setOnClickListener { setList(SortUtils.NEWEST) }
        binding.chipPopularity.setOnClickListener { setList(SortUtils.POPULARITY) }
        binding.chipVote.setOnClickListener { setList(SortUtils.VOTE) }

    }

    private fun setList(sort: String) {
        viewModel.getMovies(sort).observe(this as LifecycleOwner, moviesObserver)
    }

    private val moviesObserver = Observer<Resource<PagedList<MovieEntity>>> { movies ->
        if (movies != null) {
            when (movies.status) {
                Status.LOADING -> {
                    binding.animationLoadingMovie.visibility = View.VISIBLE
                    binding.notFound.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.animationLoadingMovie.visibility = View.GONE
                    binding.notFound.visibility = View.GONE
                    moviesAdapter.notifyDataSetChanged()
                    moviesAdapter.submitList(movies.data)
                    moviesAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    binding.animationLoadingMovie.visibility = View.GONE
                    binding.notFound.visibility = View.VISIBLE
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMoviesBinding = null
    }

}