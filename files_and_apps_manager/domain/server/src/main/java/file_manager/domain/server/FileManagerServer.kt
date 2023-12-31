package file_manager.domain.server

import file_manager.domain.server.selectable_form.SelectableForm

interface FileManagerServer {

    val hasSelected: Boolean
    val isAllSelected: Boolean
    val selectedCount: Int
    val sortingMode: SortingMode
    val selectedGroupContent: List<FileOrApp>
    var selectedGroup: GroupName

    var groups: Map<GroupName, SelectableForm<FileOrApp>>

    fun getSelected(groupName: GroupName): List<FileOrApp>

    fun isItemSelected(id: String) : Boolean

    fun switchAllSelection(groupName: GroupName)
    fun switchItemSelection(id: String)

    fun setSortingMode(sortingMode: SortingMode)

    fun clearSelected()
    fun setGroupContent(groupName: GroupName, filesOrApps: List<FileOrApp>)

}