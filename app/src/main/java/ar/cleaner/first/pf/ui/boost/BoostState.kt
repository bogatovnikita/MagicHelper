package ar.cleaner.first.pf.ui.boost

import ar.cleaner.first.pf.domain.models.BackgroundApp
import ar.cleaner.first.pf.domain.models.details.RamDetails
import ar.cleaner.first.pf.models.BackgroundAppsModel

data class BoostState(
    val isCheckedAll: Boolean = false,
    val isLoadingData: Boolean = false,
    val isDataLoaded: Boolean = false,
    val isUsageStatsAllowed: Boolean = false,
    val backgroundAppsModelList: List<BackgroundApp> = emptyList(),
    val ramDetails: RamDetails? = null
)