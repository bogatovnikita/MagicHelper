package ar.cleaner.first.pf.ui.result

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.usecases.battery.GetBatteryDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.RamDetailsUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import ar.cleaner.first.pf.extensions.mainScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val resultList: ResultListProvider,
    private val ramDetailsUseCase: RamDetailsUseCase,
    private val getBatteryDetailsUseCase: GetBatteryDetailsUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<ResultState> = MutableStateFlow(ResultState())
    val state = _state.asStateFlow()

    fun initRamDetails() {
        mainScope {
            ramDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            boostDetails = result.response,
                            itemsList = resultList.getResultList(ResultListProvider.TYPE_BOOST)
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
                            batteryDetails = result.response,
                            itemsList = resultList.getResultList(ResultListProvider.TYPE_BATTERY)
                        )
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "initBatteryDetails: Failure")
                    }
                }
            }
        }
    }

}