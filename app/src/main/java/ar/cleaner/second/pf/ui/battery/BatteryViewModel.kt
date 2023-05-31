package ar.cleaner.second.pf.ui.battery

import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.usecases.battery.BatteryDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.battery.BatteryOptimizationUseCase
import ar.cleaner.second.pf.extensions.mainScope
import ar.cleaner.second.pf.utils.bluetoothPermissionList
import ar.cleaner.second.pf.utils.event_delegate.EventDelegate
import ar.cleaner.second.pf.utils.event_delegate.EventDelegateImpl
import ar.cleaner.second.pf.utils.events.BaseEvent
import ar.cleaner.second.pf.utils.events.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BatteryViewModel @Inject constructor(
    private val list: BatteryModeFunctionList,
    private val useCaseBattery: BatteryDetailsUseCase,
    private val useCaseOptimization: BatteryOptimizationUseCase,
) : ViewModel(), EventDelegate<BaseEvent> by EventDelegateImpl() {

    private val _state: MutableStateFlow<BatteryDetails> = MutableStateFlow(BatteryDetails())
    val state = _state.asStateFlow()

    fun initBatteryDetails() {
        mainScope {
            useCaseBattery.getBatteryDetails().collect { batInfo ->
                _state.value =
                    state.value.copy(
                        batteryCharge = batInfo.batteryCharge,
                        batteryMode = batInfo.batteryMode,
                        isOptimized = batInfo.isOptimized,
                        batteryListFun = list.getListMode(batInfo.batteryMode),
                        timeToFullCharge = batInfo.timeToFullCharge,
                        isCharging = batInfo.isCharging
                    )
            }
        }
    }

    fun setBatteryMode(mode: BatteryMode) {
        useCaseOptimization.saveOptimizationMode(mode)
        _state.value =
            state.value.copy(
                batteryMode = mode,
                batteryListFun = list.getListMode(mode),
            )
    }

    fun handleBluetoothPermission() {
        sendEvent(ar.cleaner.second.pf.utils.events.RuntimePermission(bluetoothPermissionList.toList()))
    }

    fun handleRationale(event: String) {
        sendEvent(SnackbarEvent(event))
    }

    fun startOptimization() = useCaseOptimization.saveOptimizationMode(state.value.batteryMode)

}