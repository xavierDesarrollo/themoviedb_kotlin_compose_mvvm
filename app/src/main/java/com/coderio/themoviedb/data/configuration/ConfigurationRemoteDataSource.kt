package com.coderio.themoviedb.data.configuration

import com.coderio.themoviedb.api.MoviesApiClient
import com.coderio.themoviedb.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfigurationRemoteDataSource @Inject constructor(
    private val moviesApiClient: MoviesApiClient,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getApiConfiguration() = withContext(ioDispatcher) {
        moviesApiClient.getApiConfiguration()
    }
}
