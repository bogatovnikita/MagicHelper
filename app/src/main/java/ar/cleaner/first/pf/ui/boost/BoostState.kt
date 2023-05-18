package ar.cleaner.first.pf.ui.boost

import ar.cleaner.first.pf.domain.models.details.RamDetails

data class BoostState(
    val boostStatus: Boolean = false,
    val loadData: Boolean = false,
    val ramDetails: RamDetails? = null
)