package ar.cleaner.first.pf.data.providers.apps

import android.content.pm.PackageInfo

interface AppsProvider {
    suspend fun getInstalledApp(): List<PackageInfo>

    suspend fun getSystemApp(): List<PackageInfo>
}