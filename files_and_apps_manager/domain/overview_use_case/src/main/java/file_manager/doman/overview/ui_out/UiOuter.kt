package file_manager.doman.overview.ui_out

interface UiOuter {

    suspend fun close()
    suspend fun out(updateOut: UpdateOut)
    suspend fun out(allSelectionOut: AllSelectionOut)
    fun out(itemSelectionOut: ItemSelectionOut)
    suspend fun out(sortingModeOut: SortingModeOut)
    suspend fun out(groupSwitchingOut: GroupSwitchingOut)
    suspend fun showSortingSelection()
    suspend fun hideSortingSelection()
    suspend fun showDeleteDialog()
    suspend fun hideDeleteDialog()
    suspend fun showDeleteProgress()
    suspend fun showDeleteCompletion()

}