package ar.cleaner.first.pf.ui.boost

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.usecases.boosting.GetRamDetailsUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import ar.cleaner.first.pf.extensions.mainScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BoostViewModel @Inject constructor(
    private val getRamDetailsUseCase: GetRamDetailsUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<BoostState> =
        MutableStateFlow(BoostState())
    val state = _state.asStateFlow()

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
}