package yin_kio.files_and_apps_manager.presentation.scan

import file_manager.scan_progress.UiOuter

internal class UiOuterImpl(
    private val presenter: Presenter
) : UiOuter {

    var viewModel: ViewModel? = null

    override suspend fun close() {
        viewModel?.sendCommand(Command.Close)
    }

    override suspend fun requestPermission() {
        viewModel?.sendCommand(presenter.presentRequestPermissionCommand())
    }

    override suspend fun showPermissionDialog() {
        viewModel?.sendCommand(presenter.presentDialogPermissionCommand())
    }

    override suspend fun showInter() {
        viewModel?.sendCommand(Command.ShowInter)
    }

    override suspend fun showProgress() {
        viewModel?.sendCommand(Command.ShowProgress)
    }
}