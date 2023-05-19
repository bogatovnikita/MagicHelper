package ar.cleaner.first.pf.ui.boost.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.cleaner.first.pf.domain.models.details.BoostResultDetails
import ar.cleaner.first.pf.domain.usecases.boosting.BoostStatusUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.GetDetailedBoostDataUseCase
import ar.cleaner.first.pf.ui.result.ResultListProvider
import ar.cleaner.first.pf.ui.result.ResultListProvider.Companion.TYPE_BOOST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoostResultViewModel @Inject constructor(
    private val resultListProvider: ResultListProvider,
    private val boostStatusUseCase: BoostStatusUseCase,
    private val getDetailedBoostDataUseCase: GetDetailedBoostDataUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<BoostResultState> =
        MutableStateFlow(BoostResultState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = state.value.copy(
                boostStatus = boostStatusUseCase.getOptimizationStatus(),
                boostResultDetails = boostDetailsMapRamDetails(),
                resultList = resultListProvider.getResultList(TYPE_BOOST),
                loadData = true
            )
        }
        saveBoostStatus()
    }

    private fun boostDetailsMapRamDetails(): BoostResultDetails =
        getDetailedBoostDataUseCase.getBoostingDetails().let { boostResultDetails ->
            BoostResultDetails(
                usagePercents = boostResultDetails.usagePercents,
                totalRam = boostResultDetails.totalRam,
                usedRam = boostResultDetails.usedRam,
                releasedMemory = getReleasedMemory(boostResultDetails.usedRam),
                optimizedPercentages = getOptimizedPercent(boostResultDetails.usedRam)
            )
        }

    private fun getReleasedMemory(newUsageRam: Double): Double {
        val value = boostStatusUseCase.getLastOptimizeRam().convertToGb() - newUsageRam
        return if (value > 0) value else 0.0
    }

    private fun getOptimizedPercent(newUsageRam: Double): Int {
        return (((boostStatusUseCase.getLastOptimizeRam()
            .convertToGb() - newUsageRam) / newUsageRam) * 100).toInt()
    }

    private fun saveBoostStatus() {
        boostStatusUseCase.saveOptimizationStatus()
    }

    private fun Long.convertToGb(): Double = this / (1000.0 * 1000.0 * 1000.0)
}