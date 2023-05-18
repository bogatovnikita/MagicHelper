package ar.cleaner.first.pf.di

import ar.cleaner.first.pf.data.providers.temperature.TemperatureProviderImpl
import ar.cleaner.first.pf.data.repository_implementation.BatteryRepositoryImpl
import ar.cleaner.first.pf.data.repository_implementation.RamRepositoryImpl
import ar.cleaner.first.pf.data.settings.temperature.TemperatureSettingImpl
import ar.cleaner.first.pf.domain.repositorys.battery.BatteryUseCaseRepository
import ar.cleaner.first.pf.domain.repositorys.boosting.BoostingUseCaseRepository
import ar.cleaner.first.pf.domain.repositorys.temperature.TemperatureProvider
import ar.cleaner.first.pf.domain.repositorys.temperature.TemperatureSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface Binds {

    @Binds
    fun bindsBatteryRepositoryImplToBatteryUseCaseRepository(batteryRepositoryImpl: BatteryRepositoryImpl): BatteryUseCaseRepository

    @Binds
    fun bindsTemperatureSettings(settings: TemperatureSettingImpl): TemperatureSettings

    @Binds
    fun bindsTemperatureProvider(settings: TemperatureProviderImpl): TemperatureProvider

    @Binds
    fun bindsRamRepositoryImplToBoostingUseCaseRepository(ramRepositoryImpl: RamRepositoryImpl): BoostingUseCaseRepository
}