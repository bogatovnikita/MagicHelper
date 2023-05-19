package ar.cleaner.first.pf.ui.boost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.cleaner.first.pf.domain.models.details.RamDetails
import ar.cleaner.first.pf.domain.usecases.boosting.BoostStatusUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.GetDetailedBoostDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoostViewModel @Inject constructor(
    private val boostStatusUseCase: BoostStatusUseCase,
    private val getDetailedBoostDataUseCase: GetDetailedBoostDataUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<BoostState> = MutableStateFlow(BoostState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = state.value.copy(
                boostStatus = boostStatusUseCase.getOptimizationStatus(),
                ramDetails = boostDetailsMapRamDetails(),
                loadData = true
            )
        }
    }

    private fun boostDetailsMapRamDetails(): RamDetails =
        getDetailedBoostDataUseCase.getBoostingDetails().let { ramDetails ->
            RamDetails(
                usagePercents = ramDetails.usagePercents,
                totalRam = ramDetails.totalRam,
                usedRam = ramDetails.usedRam
            )
        }
}