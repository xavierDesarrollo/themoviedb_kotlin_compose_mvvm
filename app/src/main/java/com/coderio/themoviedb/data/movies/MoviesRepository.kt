package com.coderio.themoviedb.data.movies

import com.coderio.themoviedb.model.MovieSummary
import com.coderio.themoviedb.model.MovieDetails

interface MoviesRepository {
    suspend fun fetchPopularMovies(page: Int): Result<List<MovieSummary>>

    suspend fun fetchMovieDetails(id: Int): Result<MovieDetails>
}
