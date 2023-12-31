package yin_kio.files_and_apps_manager.presentation.overview

import file_manager.doman.overview.use_case.OverviewUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import yin_kio.files_and_apps_manager.presentation.overview.models.ScreenState

internal class ViewModel(
    useCase: OverviewUseCase
) : OverviewUseCase by useCase{

    private val _state = MutableStateFlow(ScreenState())
    val state = _state.asStateFlow()

    private val _command = MutableSharedFlow<Command>()
    val command = _command.asSharedFlow()

    init {
        update()
    }

    fun update(newState: (oldState: ScreenState) -> ScreenState){
        _state.value = newState(_state.value)
    }

    suspend fun sendCommand(command: Command){
        _command.emit(command)
    }

}