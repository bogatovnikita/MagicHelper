package ar.cleaner.first.pf.di

import ar.cleaner.first.pf.data.repository_implementation.BatteryRepositoryImpl
import ar.cleaner.first.pf.data.repository_implementation.CpuRepositoryImpl
import ar.cleaner.first.pf.data.repository_implementation.RamRepositoryImpl
import ar.cleaner.first.pf.domain.repositorys.battery.BatteryUseCaseRepository
import ar.cleaner.first.pf.domain.repositorys.boosting.BoostingUseCaseRepository
import ar.cleaner.first.pf.domain.repositorys.cooling.CoolingUseCaseRepository
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
    fun bindsCpuRepositoryImplToCoolingUseCaseRepository(cpuRepositoryImpl: CpuRepositoryImpl): CoolingUseCaseRepository

    @Binds
    fun bindsRamRepositoryImplToBoostingUseCaseRepository(ramRepositoryImpl: RamRepositoryImpl): BoostingUseCaseRepository
}