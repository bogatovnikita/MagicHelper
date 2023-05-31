package ar.cleaner.second.pf.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(context: Application): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile("APP_PREFS")
            }
        )
}