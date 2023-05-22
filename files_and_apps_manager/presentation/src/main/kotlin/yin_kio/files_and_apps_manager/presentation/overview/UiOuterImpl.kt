package yin_kio.files_and_apps_manager.presentation.overview

import file_manager.domain.server.GroupName
import file_manager.doman.overview.ui_out.AllSelectionOut
import file_manager.doman.overview.ui_out.GroupSwitchingOut
import file_manager.doman.overview.ui_out.ItemSelectionOut
import file_manager.doman.overview.ui_out.SortingModeOut
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.ui_out.UpdateOut

internal class UiOuterImpl(
    private val presenter: Presenter
) : UiOuter {

    var viewModel: ViewModel? = null
        set(value) {
            field = value

            viewModel?.update { it.copy(
                groupName = GroupName.Images,
                buttonText = presenter.presentButtonText(0),
                buttonAlpha = presenter.presentButtonAlpha(0),
            ) }
        }

    override suspend fun close() {
        viewModel?.sendCommand(Command.Close)
    }

    override suspend fun out(updateOut: UpdateOut) {
        viewModel?.update { it.copy(
            sortingMode = updateOut.sortingMode,
            sortingModeText = presenter.presentSortingMode(updateOut.sortingMode),
            content = presenter.presentFilesOrApps(updateOut.selectedGroupContent),
            groupName = it.groupName,
            isAllSelected = it.isAllSelected
        ) }
    }

    override suspend fun out(allSelectionOut: AllSelectionOut) {
        viewModel?.update {
            it.copy(
                isAllSelected = allSelectionOut.isAllSelected,
                buttonText = presenter.presentButtonText(allSelectionOut.selectedCount),
                buttonAlpha = presenter.presentButtonAlpha(allSelectionOut.selectedCount)
            )
        }

        viewModel?.sendCommand(Command.UpdateListContent)
    }

    override fun out(itemSelectionOut: ItemSelectionOut) {
        viewModel?.update { it.copy(
            isAllSelected = itemSelectionOut.isAllSelected,
            buttonText = presenter.presentButtonText(itemSelectionOut.selectedCount),
            buttonAlpha = presenter.presentButtonAlpha(itemSelectionOut.selectedCount)
        ) }
    }

    override suspend fun out(sortingModeOut: SortingModeOut) {
        viewModel?.update { it.copy(
            sortingMode = sortingModeOut.sortingMode,
            content = presenter.presentFilesOrApps(sortingModeOut.content),
            sortingModeText = presenter.presentSortingMode(sortingModeOut.sortingMode),
            isShowSortingSelection = false
        ) }
    }

    override suspend fun out(groupSwitchingOut: GroupSwitchingOut) {
        viewModel?.update { it.copy(
            groupName = groupSwitchingOut.groupName,
            content = presenter.presentFilesOrApps(groupSwitchingOut.content),
            buttonText = presenter.presentButtonText(groupSwitchingOut.selectionCount),
            buttonAlpha = presenter.presentButtonAlpha(groupSwitchingOut.selectionCount),
            isAllSelected = groupSwitchingOut.isAllSelected
        ) }
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