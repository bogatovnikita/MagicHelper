package yin_kio.files_and_apps_manager.presentation.overview

import android.util.Log
import file_manager.domain.server.GroupName
import file_manager.doman.overview.ui_out.AllSelectionOut
import file_manager.doman.overview.ui_out.ItemSelectionOut
import file_manager.doman.overview.ui_out.SortingModeOut
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.ui_out.UpdateOut
import kotlinx.coroutines.delay

internal class UiOuterImpl(
    private val presenter: Presenter
) : UiOuter {

    var viewModel: ViewModel? = null
        set(value) {
            field = value

            viewModel?.update { it.copy(
                groupName = GroupName.Images,
                buttonText = presenter.presentButtonText(),
                buttonAlpha = presenter.presentButtonAlpha(),
            ) }
        }

    override suspend fun close() {
        viewModel?.sendCommand(Command.Close)
    }

    override suspend fun out(updateOut: UpdateOut) {
        viewModel?.update { it.copy(
            sortingMode = updateOut.sortingMode,
            sortingModeText = presenter.presentSortingMode(updateOut.sortingMode)
        ) }
    }

    override suspend fun out(allSelectionOut: AllSelectionOut) {
        TODO("Not yet implemented")
    }

    override suspend fun out(itemSelectionOut: ItemSelectionOut) {
        TODO("Not yet implemented")
    }

    override suspend fun out(sortingModeOut: SortingModeOut) {
        //TODO добавить выбор групп
        viewModel?.update { it.copy(sortingMode = sortingModeOut.sortingMode) }
    }

    override suspend fun showGroup(groupName: GroupName) {
        viewModel?.update { it.copy(groupName = groupName) }
    }

    override suspend fun showSortingSelection() {
        viewModel?.update { it.copy(isShowSortingSelection = true) }
    }

    override suspend fun hideSortingSelection() {
        viewModel?.update { it.copy(isShowSortingSelection = false) }
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