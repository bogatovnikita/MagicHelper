package ar.cleaner.second.pf.ui.boost.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.cleaner.first.pf.domain.models.details.BoostDetails
import ar.cleaner.first.pf.domain.usecases.boosting.BoostStatusUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.GetDetailedBoostDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
        viewModelScope.launch(Dispatchers.Default) {
            _state.value = state.value.copy(
                boostStatus = boostStatusUseCase.getOptimizationStatus(),
                boostDetails = boostDetailsMapRamDetails(),
                loadData = true
            )
        }
    }

    private fun boostDetailsMapRamDetails(): BoostDetails {
        return getDetailedBoostDataUseCase.getBoostingDetails().let { boostDetails ->
            saveLastUsageRam(boostDetails.usedRamLong)
            BoostDetails(
                usagePercents = boostDetails.usagePercents,
                totalRam = boostDetails.totalRam,
                usedRam = boostDetails.usedRam
            )
        }
    }

    private fun saveLastUsageRam(value: Long) = boostStatusUseCase.saveLastOptimizeRam(value)

}