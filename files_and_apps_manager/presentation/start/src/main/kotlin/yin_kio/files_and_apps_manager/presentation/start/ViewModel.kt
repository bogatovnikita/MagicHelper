package yin_kio.files_and_apps_manager.presentation.start

import file_manager.start_screen.use_case.StartScreenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ViewModel(
    useCase: StartScreenUseCase
) : StartScreenUseCase by useCase {

    private val _state = MutableStateFlow(ScreenState())
    val state = _state.asStateFlow()

    init {
        useCase.update()
    }

    fun update(newState: (oldState: ScreenState) -> ScreenState){
        _state.value = newState(_state.value)
    }

}