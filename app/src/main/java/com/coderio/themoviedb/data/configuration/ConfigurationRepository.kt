package com.coderio.themoviedb.data.configuration

import com.coderio.themoviedb.model.ApiConfiguration

interface ConfigurationRepository {
    suspend fun fetchConfiguration(): Result<ApiConfiguration>
}
