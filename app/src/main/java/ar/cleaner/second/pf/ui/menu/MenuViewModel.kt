package ar.cleaner.second.pf.ui.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.models.details.CAN_NOT_CALCULATE_TIME
import ar.cleaner.first.pf.domain.usecases.battery.BatteryDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.RamDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.storage.StorageUseCase
import ar.cleaner.first.pf.domain.usecases.temperature.TemperatureUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import ar.cleaner.second.pf.extensions.mainScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val storageUseCase: StorageUseCase,
    private val ramDetailsUseCase: RamDetailsUseCase,
    private val batteryDetailsUseCase: BatteryDetailsUseCase,
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
            ramDetailsUseCase().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            isRamOptimized = result.response.isOptimized,
                            totalRam = result.response.totalRam,
                            usedRam = result.response.usedRam,
                            usageRamPercents = result.response.usagePercents.toFloat(),
                        )
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "${result.reason}")
                    }
                }
            }
        }
    }

    private fun updateBatteryDetails() {
        mainScope {
            batteryDetailsUseCase.getBatteryDetails().collect { batInfo ->
                val time = batInfo.timeToFullCharge
                val minutes = time.toMinutes()
                val hours = time.toHours()
                _state.value = state.value.copy(
                    isBatteryOptimized = batInfo.isOptimized,
                    batteryCharge = batInfo.batteryCharge,
                    isNeedShowTimeToFullCharge = batInfo.isCharging && time != CAN_NOT_CALCULATE_TIME,
                    timeToFullCharge = hours to minutes
                )
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
            with(storageUseCase.getStorageInfo()) {
                _state.value = state.value.copy(
                    usedMemorySize = occupied,
                    totalSize = total,
                    usageMemoryPercents = usageMemoryPercents,
                    isMemoryOptimized = storageUseCase.isStorageOptimized(),
                )
            }
        }
    }

}

fun Int.toMinutes() = (this % 3600000) / 60000

fun Int.toHours() = this / 3600000