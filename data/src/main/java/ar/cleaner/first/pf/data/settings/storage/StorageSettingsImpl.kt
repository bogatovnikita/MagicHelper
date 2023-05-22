package ar.cleaner.first.pf.data.settings.storage

import ar.cleaner.first.pf.domain.gateways.storage.StorageSettings
import yin_kio.clean_checker.CleanChecker
import javax.inject.Inject

class StorageSettingsImpl @Inject constructor(
    private val cleanChecker: CleanChecker,
) : StorageSettings {

    override val isStorageOptimized: Boolean
        get() = cleanChecker.isCleaned

}