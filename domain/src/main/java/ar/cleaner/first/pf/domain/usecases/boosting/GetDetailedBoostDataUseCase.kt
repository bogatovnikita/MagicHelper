package ar.cleaner.first.pf.domain.usecases.boosting

import ar.cleaner.first.pf.domain.models.details.BoostDetails
import ar.cleaner.first.pf.domain.repositorys.boosting.DetailedDataRepository
import javax.inject.Inject

class GetDetailedBoostDataUseCase @Inject constructor(
    private val detailedDataRepository: DetailedDataRepository
) {

    fun getBoostingDetails(): BoostDetails = detailedDataRepository.getBoostingDetails()

}