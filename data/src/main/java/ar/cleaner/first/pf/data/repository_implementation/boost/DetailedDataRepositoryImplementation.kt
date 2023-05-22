package ar.cleaner.first.pf.data.repository_implementation.boost

import ar.cleaner.first.pf.data.providers.details.DetailedDataProvider
import ar.cleaner.first.pf.domain.models.details.BoostDetails
import ar.cleaner.first.pf.domain.gateways.boosting.DetailedDataRepository
import javax.inject.Inject

class DetailedDataRepositoryImplementation @Inject constructor(
    private val detailedDataProvider: DetailedDataProvider
) :
    DetailedDataRepository {
    override fun getBoostingDetails(): BoostDetails = detailedDataProvider.getBoostDetails()
}