package com.hellu.netflixcollection.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.hellu.netflixcollection.data.source.remote.Movie

data class MovieResponse (
    @field:SerializedName("results")
    val results: List<Movie>
)