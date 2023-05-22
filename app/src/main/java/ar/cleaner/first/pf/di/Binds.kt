package ar.cleaner.first.pf.di

import ar.cleaner.first.pf.data.providers.storage.StorageInfoProvider
import ar.cleaner.first.pf.data.providers.temperature.TemperatureProviderImpl
import ar.cleaner.first.pf.data.repository_implementation.BatteryProviderImpl
import ar.cleaner.first.pf.data.repository_implementation.RamRepositoryImpl
import ar.cleaner.first.pf.data.settings.battery.BatterySettingsImpl
import ar.cleaner.first.pf.data.settings.storage.StorageSettingsImpl
import ar.cleaner.first.pf.data.settings.temperature.TemperatureSettingImpl
import ar.cleaner.first.pf.domain.gateways.battery.BatteryProvider
import ar.cleaner.first.pf.domain.gateways.battery.BatterySettings
import ar.cleaner.first.pf.domain.gateways.boosting.BoostingUseCaseRepository
import ar.cleaner.first.pf.domain.gateways.storage.Storage
import ar.cleaner.first.pf.domain.gateways.storage.StorageSettings
import ar.cleaner.first.pf.domain.gateways.temperature.TemperatureProvider
import ar.cleaner.first.pf.domain.gateways.temperature.TemperatureSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yin_kio.clean_checker.CleanChecker
import yin_kio.clean_checker.CleanCheckerImpl

@Module
@InstallIn(SingletonComponent::class)
interface Binds {

    @Binds
    fun bindsBatteryRepositoryImplToBatteryUseCaseRepository(batteryProviderImpl: BatteryProviderImpl): BatteryProvider

    @Binds
    fun bindsTemperatureSettings(settings: TemperatureSettingImpl): TemperatureSettings

    @Binds
    fun bindsTemperatureProvider(settings: TemperatureProviderImpl): TemperatureProvider

    @Binds
    fun bindsStorage(storage: StorageInfoProvider): Storage

    @Binds
    fun bindsCleanChecker(cleanChecker: CleanCheckerImpl): CleanChecker

    @Binds
    fun bindsStorageSettings(settings: StorageSettingsImpl): StorageSettings

    @Binds
    fun bindsBatterySettings(settings: BatterySettingsImpl): BatterySettings

    @Binds
    fun bindsRamRepositoryImplToBoostingUseCaseRepository(ramRepositoryImpl: RamRepositoryImpl): BoostingUseCaseRepository
}