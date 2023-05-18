package ar.cleaner.first.pf.data.providers.details

import ar.cleaner.first.pf.domain.models.details.BoostDetails

interface DetailedDataProvider {
    fun getBoostDetails(): BoostDetails
}