package ar.cleaner.first.pf.ui.cooling

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.models.details.CpuDetails
import ar.cleaner.first.pf.domain.usecases.cooling.GetCpuDetailsUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import ar.cleaner.first.pf.extensions.mainScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CoolingViewModel @Inject constructor(
    private val getCpuDetailsUseCase: GetCpuDetailsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CpuDetails> = MutableStateFlow(CpuDetails(0.0, false))
    val state = _state.asStateFlow()

    init {
        initCpuDetails()
    }

    private fun initCpuDetails() {
        mainScope {
            getCpuDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            temperature = result.response.temperature,
                            isOptimized = result.response.isOptimized
                        )
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "initCpuDetails: Failure")

                    }
                }
            }
        }
    }
}