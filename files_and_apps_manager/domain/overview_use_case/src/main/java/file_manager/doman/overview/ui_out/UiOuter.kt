package file_manager.doman.overview.ui_out

import file_manager.domain.server.GroupName

interface UiOuter {

    suspend fun close()
    suspend fun out(updateOut: UpdateOut)
    suspend fun out(allSelectionOut: AllSelectionOut)
    suspend fun out(itemSelectionOut: ItemSelectionOut)
    suspend fun out(sortingModeOut: SortingModeOut)
    suspend fun showGroup(groupName: GroupName)
    suspend fun showSortingSelection()
    suspend fun hideSortingSelection()
    suspend fun showDeleteDialog()
    suspend fun hideDeleteDialog()
    suspend fun showDeleteProgress()
    suspend fun showDeleteCompletion()

}