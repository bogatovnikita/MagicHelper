package ar.cleaner.first.pf.data.repository_implementation.boost

import ar.cleaner.first.pf.domain.models.RunningApp
import ar.cleaner.first.pf.domain.repositorys.boosting.AppListProvider

class AppListProviderImplementation : AppListProvider {
    override suspend fun getRunningApp(): List<RunningApp> {
        TODO("Not yet implemented")
    }
}