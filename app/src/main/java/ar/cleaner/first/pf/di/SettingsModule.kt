package ar.cleaner.first.pf.di

import android.content.Context
import com.bogatovnikita.settings.FunctionSettings
import com.bogatovnikita.settings.FunctionSettingsImpl
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {

    @Binds
    @Singleton
    fun bindFunctionSettingsToFunctionSettingsImpl(@ApplicationContext context: Context): FunctionSettings {
        return FunctionSettingsImpl(context = context)
    }

}