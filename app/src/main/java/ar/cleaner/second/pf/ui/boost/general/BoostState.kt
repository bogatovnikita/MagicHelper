package ar.cleaner.second.pf.ui.boost.general

import ar.cleaner.first.pf.domain.models.details.BoostDetails


data class BoostState(
    val boostStatus: Boolean = false,
    val loadData: Boolean = false,
    val boostDetails: BoostDetails? = null
)