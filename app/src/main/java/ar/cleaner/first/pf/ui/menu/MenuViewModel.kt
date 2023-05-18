package ar.cleaner.first.pf.ui.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.usecases.battery.GetBatteryDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.GetRamDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.temperature.GetCpuDetailsUseCase
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
    private val getCpuDetailsUseCase: GetCpuDetailsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MenuState> = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    fun initAllUseCase() {
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
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "MenuViewModel:initRamDetails Failure")
                    }
                }
            }
        }
    }

    private fun initBatteryDetails() {
        mainScope {
            getBatteryDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            batteryDetails = result.response
                        )
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "MenuViewModel:initBatteryDetails Failure")
                    }
                }
            }
        }
    }

    private fun initCpuDetails() {
        mainScope {
            // TODO
        }
    }

    private fun initCleanerDetails() {
        mainScope {
            // TODO("Not yet implemented")
        }
    }
}