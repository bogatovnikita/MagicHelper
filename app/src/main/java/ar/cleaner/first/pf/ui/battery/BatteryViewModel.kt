package ar.cleaner.first.pf.ui.battery

import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.usecases.battery.BatteryDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.battery.BatteryOptimizationUseCase
import ar.cleaner.first.pf.extensions.mainScope
import ar.cleaner.first.pf.utils.bluetoothPermissionList
import ar.cleaner.first.pf.utils.event_delegate.EventDelegate
import ar.cleaner.first.pf.utils.event_delegate.EventDelegateImpl
import ar.cleaner.first.pf.utils.events.BaseEvent
import ar.cleaner.first.pf.utils.events.RuntimePermission
import ar.cleaner.first.pf.utils.events.SnackbarEvent
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
                    )
            }
        }
    }

    fun setBatteryMode(mode: BatteryMode) {
        _state.value =
            state.value.copy(
                batteryMode = mode,
                batteryListFun = list.getListMode(mode),
            )
    }

    fun handleBluetoothPermission() {
        sendEvent(RuntimePermission(bluetoothPermissionList.toList()))
    }

    fun handleRationale(event: String) {
        sendEvent(SnackbarEvent(event))
    }

    fun startOptimization() = useCaseOptimization.saveOptimizationMode(state.value.batteryMode)

}