package com.hellu.netflixcollection.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.hellu.netflixcollection.data.source.remote.TVShow

data class TVShowResponse(
    @field:SerializedName("results")
    var results: List<TVShow>
)