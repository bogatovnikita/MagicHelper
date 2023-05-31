package ar.cleaner.second.pf.ui.boost.result

import ar.cleaner.first.pf.domain.models.details.BoostResultDetails
import ar.cleaner.second.pf.models.MenuHorizontalItems

data class BoostResultState(
    val boostStatus: Boolean = false,
    val loadData: Boolean = false,
    val boostResultDetails: BoostResultDetails? = null,
    val resultList: List<MenuHorizontalItems> = emptyList()
)
