package ar.cleaner.first.pf.ui.result

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.domain.usecases.battery.GetBatteryDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.GetRamDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.temperature.GetCpuDetailsUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import ar.cleaner.first.pf.extensions.mainScope
import ar.cleaner.first.pf.models.MenuHorizontalItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val getRamDetailsUseCase: GetRamDetailsUseCase,
    private val getBatteryDetailsUseCase: GetBatteryDetailsUseCase,
    private val getCpuDetailsUseCase: GetCpuDetailsUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<ResultState> = MutableStateFlow(ResultState())
    val state = _state.asStateFlow()

    private val menuHorizontalItems: MutableList<MenuHorizontalItems> = mutableListOf()


    init {
        initList()
    }

    private fun initList() {
        menuHorizontalItems.add(
            MenuHorizontalItems(
                2,
                R.string.boost_title_name,
                R.drawable.ic_boost,
                R.string.speed_up_the_work_of_your_phone
            )
        )
        menuHorizontalItems.add(
            MenuHorizontalItems(
                1, R.string.battery_power, R.drawable.ic_battery,
                R.string.extend_the_operation_of_your_phone
            )
        )
        menuHorizontalItems.add(
            MenuHorizontalItems(
                3,
                R.string.temperature_title,
                R.drawable.ic_cpu,
                R.string.temperature_need_check
            )
        )
        menuHorizontalItems.add(
            MenuHorizontalItems(
                4,
                R.string.clear_junk,
                R.drawable.ic_junk,
                R.string.remove_the_trash
            )
        )
        _state.value = state.value.copy(itemsList = menuHorizontalItems)
    }

    fun initRamDetails() {
        mainScope {
            getRamDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            ramDetails = result.response
                        )
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "initRamDetails: Failure")
                    }
                }
            }
        }
    }

    fun initBatteryDetails() {
        mainScope {
            getBatteryDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            batteryDetails = result.response
                        )
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "initBatteryDetails: Failure")
                    }
                }
            }
        }
    }

    fun initCpuDetails() {
        mainScope {
                // TODO
        }
    }

    fun initCleanerDetails() {
        mainScope {
            // TODO("Not yet implemented")
        }
    }
}