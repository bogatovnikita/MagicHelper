package yin_kio.files_and_apps_manager.presentation.scan

import file_manager.scan_progress.UiOuter

internal class UiOuterImpl : UiOuter {

    var viewModel: ViewModel? = null

    override suspend fun close() {
        viewModel?.sendCommand(Command.Close)
    }

    override suspend fun requestPermission() {
        viewModel?.sendCommand(Command.RequestPermission)
    }

    override suspend fun showPermissionDialog() {
        viewModel?.sendCommand(Command.ShowPermissionDialog)
    }

    override suspend fun showInter() {
        viewModel?.sendCommand(Command.ShowInter)
    }

    override suspend fun showProgress() {
        viewModel?.sendCommand(Command.ShowProgress)
    }
}