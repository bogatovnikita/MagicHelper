package file_manager.domain.server

import file_manager.domain.server.selectable_form.SelectableForm

interface FileManagerServer {

    val hasSelected: Boolean
    val isAllSelected: Boolean
    val selectedCount: Int
    val sortingMode: SortingMode

    var groups: Map<GroupName, SelectableForm<FileOrApp>>

    fun getSelected(groupName: GroupName): List<FileOrApp>

    fun isItemSelected(groupName: GroupName, id: String) : Boolean

    fun switchAllSelection(groupName: GroupName)
    fun switchItemSelection(groupName: GroupName, id: String)

    fun setSortingMode(sortingMode: SortingMode)


}