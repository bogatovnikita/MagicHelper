package ar.cleaner.first.pf.di

import android.app.Application
import com.bogatovnikita.settings.FunctionSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {

    @Provides
    @Singleton
    fun provideSettings(application: Application): FunctionSettings = FunctionSettings(application)

}