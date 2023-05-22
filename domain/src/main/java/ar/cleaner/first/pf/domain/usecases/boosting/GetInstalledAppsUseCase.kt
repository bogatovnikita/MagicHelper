package ar.cleaner.first.pf.domain.usecases.boosting

import ar.cleaner.first.pf.domain.models.RunningApp
import ar.cleaner.first.pf.domain.gateways.boosting.AppListProvider
import javax.inject.Inject

class GetInstalledAppsUseCase @Inject constructor(private val appListProvider: AppListProvider) {

    suspend fun getRunningApp(): List<RunningApp> = appListProvider.getRunningApp()

}