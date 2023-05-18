package ar.cleaner.first.pf.domain.repositorys.boosting

import ar.cleaner.first.pf.domain.models.RunningApp

interface AppListProvider {

    suspend fun getRunningApp(): List<RunningApp>

}