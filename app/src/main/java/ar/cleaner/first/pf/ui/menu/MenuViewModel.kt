package ar.cleaner.first.pf.ui.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.usecases.battery.GetBatteryDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.GetRamDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.temperature.TemperatureUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import ar.cleaner.first.pf.extensions.mainScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getRamDetailsUseCase: GetRamDetailsUseCase,
    private val getBatteryDetailsUseCase: GetBatteryDetailsUseCase,
    private val temperatureUseCase: TemperatureUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MenuState> = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    fun updateAllInfo() {
        updateRamDetails()
        updateBatteryDetails()
        updateFileManagerDetails()
        updateTemperatureDetails()
    }

    private fun updateRamDetails() {
        mainScope {
            getRamDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            totalRam = result.response.totalRam,
                            usedRam = result.response.usedRam,
                            usageRamPercents = result.response.usagePercents.toFloat(),
                        )
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "MenuViewModel:initRamDetails Failure")
                    }
                }
            }
        }
    }

    private fun updateBatteryDetails() {
        mainScope {
            getBatteryDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            isBatteryOptimized = result.response.isOptimized,
                            batteryCharge = result.response.batteryCharge
                        )
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "MenuViewModel:initBatteryDetails Failure")
                    }
                }
            }
        }
    }

    private fun updateTemperatureDetails() {
        mainScope {
            _state.value = state.value.copy(
                isTemperatureChecked = temperatureUseCase.isOptimized()
            )
        }
    }

    private fun updateFileManagerDetails() {
        mainScope {
            // TODO("Not yet implemented")
        }
    }
}