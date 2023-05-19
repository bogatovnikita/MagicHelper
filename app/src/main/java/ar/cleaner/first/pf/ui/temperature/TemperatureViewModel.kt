package ar.cleaner.first.pf.ui.temperature

import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.models.details.TemperatureDetails
import ar.cleaner.first.pf.domain.usecases.temperature.TemperatureUseCase
import ar.cleaner.first.pf.extensions.mainScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TemperatureViewModel @Inject constructor(
    private val useCase: TemperatureUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<TemperatureDetails> = MutableStateFlow(TemperatureDetails(0, false))
    val state = _state.asStateFlow()

    fun initCpuDetails() {
        mainScope {
            _state.value = state.value.copy(
                temperature = useCase.getTemperature(),
                isTemperatureChecked = useCase.isOptimized(),
            )
        }
    }
}