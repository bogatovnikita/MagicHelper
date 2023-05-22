package ar.cleaner.first.pf.domain.usecases.storage

import ar.cleaner.first.pf.domain.gateways.storage.Storage
import ar.cleaner.first.pf.domain.gateways.storage.StorageSettings
import javax.inject.Inject

class StorageUseCase @Inject constructor(
    private val storage: Storage,
    private val storageSettings: StorageSettings,
) {

    fun getStorageInfo() = storage.getStorageInfo()

    fun isStorageOptimized() = storageSettings.isStorageOptimized

}