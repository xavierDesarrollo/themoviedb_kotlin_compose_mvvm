package com.coderio.themoviedb.api

import com.coderio.themoviedb.api.responses.ConfigurationResponse
import com.coderio.themoviedb.api.responses.MovieResponse
import com.coderio.themoviedb.api.responses.PopularMoviesResponse

interface MoviesApiClient {
    suspend fun getApiConfiguration(): Result<ConfigurationResponse>

    suspend fun getPopularMovies(page: Int): Result<PopularMoviesResponse>

    suspend fun getMovieDetails(id: Int): Result<MovieResponse>
}
