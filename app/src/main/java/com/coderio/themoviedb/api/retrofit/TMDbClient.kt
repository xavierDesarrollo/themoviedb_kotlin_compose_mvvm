package com.coderio.themoviedb.api.retrofit

import com.coderio.themoviedb.api.MoviesApiClient
import com.coderio.themoviedb.api.responses.ConfigurationResponse
import com.coderio.themoviedb.api.responses.MovieResponse
import com.coderio.themoviedb.api.responses.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbClient : MoviesApiClient {

    @GET("configuration")
    override suspend fun getApiConfiguration(): Result<ConfigurationResponse>

    @GET("movie/popular")
    override suspend fun getPopularMovies(@Query("page") page: Int): Result<PopularMoviesResponse>

    @GET("movie/{id}")
    override suspend fun getMovieDetails(@Path("id") id: Int): Result<MovieResponse>
}
