package ar.cleaner.first.pf.domain.usecases.storage

import ar.cleaner.first.pf.domain.gateways.storage.Storage
import javax.inject.Inject

class StorageUseCase @Inject constructor(
    private val storage: Storage
) {

    fun getStorageInfo() = storage.getStorageInfo()

}