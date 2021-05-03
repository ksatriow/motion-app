package com.hellu.netflixcollection.data.source.remote

import com.google.gson.annotations.SerializedName

data class TVShow(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("original_language")
    val originalLanguage: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("vote_count")
    val voteCount: Int,

    @field:SerializedName("poster_path")
    val posterPath: String
)