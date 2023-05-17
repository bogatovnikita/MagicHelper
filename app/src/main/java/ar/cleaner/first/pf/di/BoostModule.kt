package ar.cleaner.first.pf.di

import ar.cleaner.first.pf.data.providers.apps.AppsProvider
import ar.cleaner.first.pf.data.providers.apps.AppsProviderImpl
import ar.cleaner.first.pf.data.providers.kill_background_processes.KillBackgroundProcessesProvider
import ar.cleaner.first.pf.data.providers.kill_background_processes.KillBackgroundProcessesProviderImpl
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

    @Binds
    @Singleton
    abstract fun bindKillBackgroundProcessesProviderToKillBackgroundProcessesProviderImpl(
        killBackgroundProcessesProviderImpl: KillBackgroundProcessesProviderImpl
    ): KillBackgroundProcessesProvider

    @Binds
    @Singleton
    abstract fun bindAppsProviderToAppsProviderImpl(appsProviderImpl: AppsProviderImpl): AppsProvider
}
