package ar.cleaner.first.pf.di

import ar.cleaner.first.pf.data.repository_implementation.boost.AppListProviderImplementation
import ar.cleaner.first.pf.data.repository_implementation.boost.BoostStatusRepositoryImplementation
import ar.cleaner.first.pf.data.repository_implementation.boost.DetailedDataRepositoryImplementation
import ar.cleaner.first.pf.data.repository_implementation.boost.KillBackgroundProcessImplementation
import ar.cleaner.first.pf.domain.repositorys.boosting.AppListProvider
import ar.cleaner.first.pf.domain.repositorys.boosting.BoostStatusRepository
import ar.cleaner.first.pf.domain.repositorys.boosting.DetailedDataRepository
import ar.cleaner.first.pf.domain.repositorys.boosting.KillBackgroundProcess
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
    abstract fun bindKillBackgroundProcessToKillBackgroundProcessImplementation(
        killBackgroundProcessImplementation: KillBackgroundProcessImplementation
    ): KillBackgroundProcess

    @Binds
    @Singleton
    abstract fun bindDetailedDataRepositoryToDetailedDataRepositoryImplementation(
        detailedDataRepositoryImplementation: DetailedDataRepositoryImplementation
    ): DetailedDataRepository

    @Binds
    @Singleton
    abstract fun bindBoostStatusRepositoryToBoostStatusRepositoryImplementation(
        boostStatusRepositoryImplementation: BoostStatusRepositoryImplementation
    ): BoostStatusRepository

    @Binds
    @Singleton
    abstract fun bindAppListProviderToAppListProviderImplementation(
        appListProviderImplementation: AppListProviderImplementation
    ): AppListProvider


}
