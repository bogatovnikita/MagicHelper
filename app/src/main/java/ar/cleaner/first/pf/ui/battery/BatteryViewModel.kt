package ar.cleaner.first.pf.ui.battery

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.models.BatteryTime
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.models.details.CpuDetails
import ar.cleaner.first.pf.domain.usecases.battery.GetBatteryDetailsUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import ar.cleaner.first.pf.extensions.mainScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BatteryViewModel @Inject constructor(
    private val getBatteryDetailsUseCase: GetBatteryDetailsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<BatteryDetails> = MutableStateFlow(
        BatteryDetails(
            0, BatteryMode.MEDIUM,
            BatteryTime(0, 0), false
        )
    )
    val state = _state.asStateFlow()

    fun initBatteryDetails() {
        mainScope {
            getBatteryDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value =
                            state.value.copy(
                                batteryCharge = result.response.batteryCharge,
                                batteryMode = result.response.batteryMode,
                                batteryRemainingTime = result.response.batteryRemainingTime,
                                isOptimized = result.response.isOptimized
                            )
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "BatteryViewModel:initBatteryDetails Failure")
                    }
                }

            }
        }
    }
}