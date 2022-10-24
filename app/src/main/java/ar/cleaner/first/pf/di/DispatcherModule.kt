package ar.cleaner.first.pf.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

}