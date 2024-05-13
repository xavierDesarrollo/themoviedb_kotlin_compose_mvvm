package com.coderio.themoviedb.di

import com.coderio.themoviedb.data.configuration.ConfigurationRemoteDataSource
import com.coderio.themoviedb.data.configuration.ConfigurationRepository
import com.coderio.themoviedb.data.configuration.ConfigurationRepositoryImpl
import com.coderio.themoviedb.data.movies.MoviesRemoteDataSource
import com.coderio.themoviedb.data.movies.MoviesRepository
import com.coderio.themoviedb.data.movies.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    fun provideMoviesRepository(
        moviesRemoteDataSource: MoviesRemoteDataSource,
        configurationRepository: ConfigurationRepository
    ): MoviesRepository =
        MoviesRepositoryImpl(moviesRemoteDataSource, configurationRepository)

    @Provides
    fun provideConfigurationRepository(
        configurationRemoteDataSource: ConfigurationRemoteDataSource
    ): ConfigurationRepository =
        ConfigurationRepositoryImpl(configurationRemoteDataSource)
}
