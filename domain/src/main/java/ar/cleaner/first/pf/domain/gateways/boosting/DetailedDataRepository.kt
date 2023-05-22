package ar.cleaner.first.pf.domain.gateways.boosting

import ar.cleaner.first.pf.domain.models.details.BoostDetails

interface DetailedDataRepository {

    fun getBoostingDetails(): BoostDetails

}