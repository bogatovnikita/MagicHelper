package ar.cleaner.second.pf.di

import ar.cleaner.first.pf.data.clean_checker.LastCleanTimeImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yin_kio.clean_checker.CleanCheckerImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    @Provides
    @Singleton
    fun provideCleanCheckerImpl(lastCleanTime: LastCleanTimeImpl): CleanCheckerImpl =
        CleanCheckerImpl(lastCleanTime)

}