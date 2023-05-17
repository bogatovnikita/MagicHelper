package file_manager.doman.overview.use_case

import file_manager.domain.server.GroupName

interface OverviewUseCase {
    fun close()
    fun update()
    fun switchGroup(groupName: GroupName)
    fun switchAllSelection(groupName: GroupName)
    fun showSortingSelection()
    fun switchItemSelection(groupName: GroupName, itemId: String)
    fun showDeleteDialog()
    fun delete(groupName: GroupName)
    fun hideDeleteDialog()
    fun completeDelete()
}