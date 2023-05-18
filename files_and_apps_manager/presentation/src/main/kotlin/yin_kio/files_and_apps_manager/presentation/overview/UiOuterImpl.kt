package yin_kio.files_and_apps_manager.presentation.overview

import file_manager.domain.server.GroupName
import file_manager.doman.overview.ui_out.AllSelectionOut
import file_manager.doman.overview.ui_out.ItemSelectionOut
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.ui_out.UpdateOut

internal class UiOuterImpl : UiOuter {

    var viewModel: ViewModel? = null

    override suspend fun close() {
        viewModel?.sendCommand(Command.Close)
    }

    override suspend fun out(updateOut: UpdateOut) {
        TODO("Not yet implemented")
    }

    override suspend fun out(allSelectionOut: AllSelectionOut) {
        TODO("Not yet implemented")
    }

    override suspend fun out(itemSelectionOut: ItemSelectionOut) {
        TODO("Not yet implemented")
    }

    override suspend fun showGroup(groupName: GroupName) {
        viewModel?.update { it.copy(groupName = groupName) }
    }

    override suspend fun showSortingSelection() {
        TODO("Not yet implemented")
    }

    override suspend fun showDeleteDialog() {
        viewModel?.sendCommand(Command.ShowAskDeleteDialog)
    }

    override suspend fun hideDeleteDialog() {
        viewModel?.sendCommand(Command.HideAskDeleteDialog)
    }

    override suspend fun showDeleteProgress() {
        viewModel?.sendCommand(Command.ShowDeleteProgress)
    }

    override suspend fun showDeleteCompletion() {
        viewModel?.sendCommand(Command.ShowDeleteCompletion)
    }
}