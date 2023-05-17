package ar.cleaner.first.pf.di

import ar.cleaner.first.pf.data.repository_implementation.BoostRepositoryImplementation
import ar.cleaner.first.pf.domain.repositorys.boosting.BoostRepository
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BoostModule {

    @Binds
    @Singleton
    abstract fun bindBoostRepositoryToBoostRepositoryImplementation(
        boostRepositoryImplementation: BoostRepositoryImplementation
    ): BoostRepository
}
