package ar.cleaner.first.pf.ui.boost

import ar.cleaner.first.pf.domain.models.details.RamDetails

data class BoostState(
    val isCheckedAll: Boolean = false,
    val isLoadingData: Boolean = false,
    val isDataLoaded: Boolean = false,
    val isUsageStatsAllowed: Boolean = false,
    val ramDetails: RamDetails? = null
)