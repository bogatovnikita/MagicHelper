package ar.cleaner.first.pf.di

import ar.cleaner.first.pf.data.providers.storage.StorageInfoProvider
import ar.cleaner.first.pf.data.providers.temperature.TemperatureProviderImpl
import ar.cleaner.first.pf.data.repository_implementation.BatteryRepositoryImpl
import ar.cleaner.first.pf.data.repository_implementation.RamRepositoryImpl
import ar.cleaner.first.pf.data.settings.temperature.TemperatureSettingImpl
import ar.cleaner.first.pf.domain.gateways.battery.BatteryUseCaseRepository
import ar.cleaner.first.pf.domain.gateways.boosting.BoostingUseCaseRepository
import ar.cleaner.first.pf.domain.gateways.storage.Storage
import ar.cleaner.first.pf.domain.gateways.temperature.TemperatureProvider
import ar.cleaner.first.pf.domain.gateways.temperature.TemperatureSettings
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
    fun bindsStorage(storage: StorageInfoProvider): Storage


    @Binds
    fun bindsRamRepositoryImplToBoostingUseCaseRepository(ramRepositoryImpl: RamRepositoryImpl): BoostingUseCaseRepository
}