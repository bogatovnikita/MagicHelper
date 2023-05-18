package ar.cleaner.first.pf.ui.temperature

import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.models.details.CpuDetails
import ar.cleaner.first.pf.domain.usecases.temperature.GetCpuDetailsUseCase
import ar.cleaner.first.pf.extensions.mainScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TemperatureViewModel @Inject constructor(
    private val useCase: GetCpuDetailsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CpuDetails> = MutableStateFlow(CpuDetails(0, false))
    val state = _state.asStateFlow()

    fun initCpuDetails() {
        mainScope {
            _state.value = state.value.copy(
                temperature = useCase.getTemperature(),
                isOptimized = useCase.isOptimized(),
                loadingIsDone = true
            )
        }
    }
}