package com.coderio.themoviedb.di

import com.coderio.themoviedb.data.movies.MoviesRepository
import com.coderio.themoviedb.data.movies.TestMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoriesModule::class]
)
class TestRepositoriesModule {

    @Provides
    fun provideMoviesRepository(): MoviesRepository = TestMoviesRepository()
}
