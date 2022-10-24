package ar.cleaner.first.pf.ui.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.cleaner.first.pf.domain.usecases.battery.GetBatteryDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.GetRamDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.cooling.GetCpuDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.junk.GetCleanerDetailsUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import ar.cleaner.first.pf.extensions.mainScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getRamDetailsUseCase: GetRamDetailsUseCase,
    private val getBatteryDetailsUseCase: GetBatteryDetailsUseCase,
    private val getCleanerDetailsUseCase: GetCleanerDetailsUseCase,
    private val getCpuDetailsUseCase: GetCpuDetailsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MenuState> = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    init {
        initRamDetails()
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
                        Log.e("pie", "initRamDetails: Failure")
                    }
                }
            }
        }
    }
}