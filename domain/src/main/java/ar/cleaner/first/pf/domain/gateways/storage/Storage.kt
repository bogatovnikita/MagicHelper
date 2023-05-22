package ar.cleaner.first.pf.domain.gateways.storage

import ar.cleaner.first.pf.domain.models.StorageInfo

interface Storage {

    fun getStorageInfo() : StorageInfo

}