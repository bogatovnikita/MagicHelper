package yin_kio.files_and_apps_manager.presentation.scan

import file_manager.scan_progress.ScanProgressUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class ViewModel(
    useCase: ScanProgressUseCase
) : ScanProgressUseCase by useCase{

    private val _command = MutableSharedFlow<Command>()
    val command = _command.asSharedFlow()

    init {
        useCase.scan()
    }

    suspend fun sendCommand(command: Command){
        _command.emit(command)
    }

}