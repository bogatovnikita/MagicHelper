package ar.cleaner.first.pf.ui.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.domain.models.CleanerDetails
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.models.details.CpuDetails
import ar.cleaner.first.pf.domain.models.details.RamDetails
import ar.cleaner.first.pf.domain.usecases.battery.GetBatteryDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.GetRamDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.cooling.GetCpuDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.junk.GetCleanerDetailsUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import ar.cleaner.first.pf.extensions.mainScope
import ar.cleaner.first.pf.models.MenuItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getRamDetailsUseCase: GetRamDetailsUseCase,
    private val getBatteryDetailsUseCase: GetBatteryDetailsUseCase,
    private val getCleanerDetailsUseCase: GetCleanerDetailsUseCase,
    private val getCpuDetailsUseCase: GetCpuDetailsUseCase,
    private val context: Application
) : ViewModel() {

    private val _state: MutableStateFlow<MenuState> = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    private val menuItemsMap: MutableMap<String, MenuItems> = mutableMapOf()

    init {
        initRamDetails()
        initBatteryDetails()
        initCleanerDetails()
        initCpuDetails()
    }

    private fun initRamDetails() {
        mainScope {
            getRamDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            ramDetails = result.response
                        )
                        mapToMenuItemRam(result.response)
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "initRamDetails: Failure")
                    }
                }
            }
        }
    }

    private fun mapToMenuItemRam(response: RamDetails) {
        val itemRam = MenuItems(
            id = 1,
            title = context.getString(R.string.boost),
            icon = R.drawable.ic_boost,
            descriptionIsOptimize = context.getString(R.string.boost_description),
            descriptionNotOptimize = context.getString(R.string.boost_description_not_optimize),
            optimizeIsDone = response.isOptimized
        )
        menuItemsMap[BOOST_KEY] = itemRam
        _state.value = state.value.copy(menuItemList = menuItemsMap)
    }

    private fun initBatteryDetails() {
        mainScope {
            getBatteryDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            batteryDetails = result.response
                        )
                        mapToMenuBatteryItem(result.response)
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "initBatteryDetails: Failure")
                    }
                }
            }
        }
    }

    private fun mapToMenuBatteryItem(response: BatteryDetails) {
        val itemBattery = MenuItems(
            id = 2,
            title = context.getString(R.string.battery_power),
            icon = R.drawable.ic_battery,
            descriptionIsOptimize = context.getString(
                R.string.battery_power_description_D_D,
                response.batteryRemainingTime.hour,
                response.batteryRemainingTime.minute
            ),
            descriptionNotOptimize = context.getString(
                R.string.battery_power_description_D_D,
                response.batteryRemainingTime.hour,
                response.batteryRemainingTime.minute
            ),
            optimizeIsDone = response.isOptimized
        )
        menuItemsMap[BATTERY_KEY] = itemBattery
        _state.value = state.value.copy(menuItemList = menuItemsMap)
    }

    private fun initCpuDetails() {
        mainScope {
            getCpuDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            cpuDetails = result.response
                        )
                        mapToMenuCpuItem(result.response)
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "initCpuDetails: Failure")

                    }
                }
            }
        }
    }

    private fun mapToMenuCpuItem(response: CpuDetails) {
        val itemCpu = MenuItems(
            id = 3,
            title = context.getString(R.string.cooling_cpu),
            icon = R.drawable.ic_cpu,
            descriptionIsOptimize = context.getString(R.string.clean_junk_done),
            descriptionNotOptimize = context.getString(R.string.cooling_cpu_description_not_optimized),
            optimizeIsDone = response.isOptimized
        )
        menuItemsMap[CPU_KEY] = itemCpu
        _state.value = state.value.copy(menuItemList = menuItemsMap)
    }

    private fun initCleanerDetails() {
        mainScope {
            getCleanerDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            cleanerDetails = result.response
                        )
                        mapToMenuCleanerItem(result.response)
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "initCleanerDetails: Failure")
                    }
                }
            }
        }
    }

    private fun mapToMenuCleanerItem(response: CleanerDetails) {
        Log.e("pie", "mapToMenuCleanerItem: $response")
        val itemCleaner = MenuItems(
            id = 4,
            title = context.getString(R.string.clear_junk),
            icon = R.drawable.ic_junk,
            descriptionIsOptimize = context.getString(R.string.clean_junk_done),
            descriptionNotOptimize = context.getString(
                R.string.clean_junk_description_not_optimized_D,
                response.trashSize
            ),
            optimizeIsDone = response.isOptimized
        )
        menuItemsMap[JUNK_KEY] = itemCleaner
        _state.value = state.value.copy(menuItemList = menuItemsMap)
    }

    companion object {
        const val BOOST_KEY = "BOOST_KEY"
        const val BATTERY_KEY = "BATTERY_KEY"
        const val CPU_KEY = "CPU_KEY"
        const val JUNK_KEY = "JUNK_KEY"
    }
}