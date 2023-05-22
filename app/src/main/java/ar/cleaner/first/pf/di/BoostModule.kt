package ar.cleaner.first.pf.di

import ar.cleaner.first.pf.data.providers.apps.AppsProvider
import ar.cleaner.first.pf.data.providers.apps.AppsProviderImpl
import ar.cleaner.first.pf.data.providers.details.DetailedDataProvider
import ar.cleaner.first.pf.data.providers.details.DetailedDataProviderImpl
import ar.cleaner.first.pf.data.providers.kill_background_processes.KillBackgroundProcessesProvider
import ar.cleaner.first.pf.data.providers.kill_background_processes.KillBackgroundProcessesProviderImpl
import ar.cleaner.first.pf.data.repository_implementation.boost.AppListProviderImplementation
import ar.cleaner.first.pf.data.repository_implementation.boost.BoostStatusRepositoryImplementation
import ar.cleaner.first.pf.data.repository_implementation.boost.DetailedDataRepositoryImplementation
import ar.cleaner.first.pf.data.repository_implementation.boost.KillBackgroundProcessImplementation
import ar.cleaner.first.pf.domain.gateways.boosting.AppListProvider
import ar.cleaner.first.pf.domain.gateways.boosting.BoostStatusRepository
import ar.cleaner.first.pf.domain.gateways.boosting.DetailedDataRepository
import ar.cleaner.first.pf.domain.gateways.boosting.KillBackgroundProcess
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BoostModule {

    @Provides
    @Singleton
    fun provideKillBackgroundProcess(
        killBackgroundProcessImplementation: KillBackgroundProcessImplementation
    ): KillBackgroundProcess {
        return killBackgroundProcessImplementation
    }

    @Provides
    @Singleton
    fun provideKillBackgroundProcessesProvider(
        killBackgroundProcessesProviderImpl: KillBackgroundProcessesProviderImpl
    ): KillBackgroundProcessesProvider {
        return killBackgroundProcessesProviderImpl
    }

    @Provides
    @Singleton
    fun provideDetailedDataRepository(
        detailedDataRepositoryImplementation: DetailedDataRepositoryImplementation
    ): DetailedDataRepository {
        return detailedDataRepositoryImplementation
    }

    @Provides
    @Singleton
    fun provideDetailedDataProvider(
        detailedDataProviderImpl: DetailedDataProviderImpl
    ): DetailedDataProvider {
        return detailedDataProviderImpl
    }

    @Provides
    @Singleton
    fun provideBoostStatusRepository(
        boostStatusRepositoryImplementation: BoostStatusRepositoryImplementation
    ): BoostStatusRepository {
        return boostStatusRepositoryImplementation
    }

    @Provides
    @Singleton
    fun provideAppListProvider(
        appListProviderImplementation: AppListProviderImplementation
    ): AppListProvider {
        return appListProviderImplementation
    }

    @Provides
    @Singleton
    fun provideAppsProvider(
        appsProviderImpl: AppsProviderImpl
    ): AppsProvider {
        return appsProviderImpl
    }

}
