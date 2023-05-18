package file_manager.doman.overview.use_case

import file_manager.domain.server.GroupName
import file_manager.domain.server.SortingMode

interface OverviewUseCase {
    fun close()
    fun update()
    fun switchGroup(groupName: GroupName)
    fun switchAllSelection(groupName: GroupName)
    fun showSortingSelection()
    fun hideSortingSelection()
    fun switchItemSelection(groupName: GroupName, itemId: String)
    fun showAskDeleteDialog()
    fun delete(groupName: GroupName)
    fun hideAskDeleteDialog()
    fun completeDelete()
    fun setSortingMode(sortingMode: SortingMode)
}