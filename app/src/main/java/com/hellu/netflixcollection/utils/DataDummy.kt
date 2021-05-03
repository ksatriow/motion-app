package com.hellu.netflixcollection.utils

import com.hellu.netflixcollection.data.source.local.entity.MovieEntity
import com.hellu.netflixcollection.data.source.remote.Movie
import com.hellu.netflixcollection.data.source.remote.TVShow

object DataDummy {

    fun generateDummyMovies(): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()
        movies.add(
            MovieEntity(
                "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a \\\"nobody.\\\" When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
                "en",
                "2021-03-18",
                2703.802,
                8.5,
                615457,
                "Nobody",
                735,
                "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg"
            )
        )
        movies.add(
            MovieEntity(
                "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a \\\"nobody.\\\" When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
                "en",
                "2021-03-18",
                2703.802,
                8.5,
                5457,
                "Nobody",
                735,
                "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg",
                true,
                true
            )
        )
        return movies
    }

    fun generateDummyTVShows(): List<MovieEntity> {
        val tvshows = ArrayList<MovieEntity>()
        tvshows.add(
            MovieEntity(
                "After years of facing megalomaniacal supervillains, monsters wreaking havoc on Metropolis, and alien invaders intent on wiping out the human race, The Man of Steel aka Clark Kent and Lois Lane come face to face with one of their greatest challenges ever: dealing with all the stress, pressures and complexities that come with being working parents in today's society.",
                "en",
                "2021-02-23",
                574.473,
                8.3,
                95057,
                "Superman & Lois",
                748,
                "/pPKiIJEEcV0E1hpVcWRXyp73ZpX.jpg"
            )
        )
        tvshows.add(
            MovieEntity(
                "2005-03-27",
                "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
                "en",
                871.161,
                8.1,
                1416,
                "Grey's Anatomy",
                4554,
                "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
                true,
                true
            )
        )
        return tvshows
    }

    fun generateDummyRemoteMovies(): List<Movie> {
        val movies = ArrayList<Movie>()
        movies.add(
            Movie(
                615457,
                "Nobody",
                "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a \\\"nobody.\\\" When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
                "en",
                "2021-03-18",
                8.5,
                2703.802,
                735,
                "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg"
            )
        )
        movies.add(
            Movie(
                615457,
                "Nobody",
                "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a \\\"nobody.\\\" When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
                "en",
                "2021-03-18",
                8.5,
                2703.802,
                735,
                "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg"
            )
        )
        return movies
    }

    fun generateDummyRemoteTVShow(): List<TVShow> {
        val tvshows = ArrayList<TVShow>()
        tvshows.add(
            TVShow(
                95057,
                "Superman & Lois",
                "After years of facing megalomaniacal supervillains, monsters wreaking havoc on Metropolis, and alien invaders intent on wiping out the human race, The Man of Steel aka Clark Kent and Lois Lane come face to face with one of their greatest challenges ever: dealing with all the stress, pressures and complexities that come with being working parents in today's society.",
                "en",
                "2021-02-23",
                8.3,
                574.473,
                748,
                "/pPKiIJEEcV0E1hpVcWRXyp73ZpX.jpg"
            )
        )
        tvshows.add(
            TVShow(
                1416,
                "Grey's Anatomy",
                "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
                "en",
                "2005-03-27",
                8.1,
                871.161,
                4554,
                "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg"
            )
        )
        return tvshows
    }

}