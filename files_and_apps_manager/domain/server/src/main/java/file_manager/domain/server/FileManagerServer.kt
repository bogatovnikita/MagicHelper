package file_manager.domain.server

import file_manager.domain.server.selectable_form.SelectableForm

interface FileManagerServer {

    val hasSelected: Boolean
    val isAllSelected: Boolean
    val selectedCount: Int

    var groups: Map<GroupName, SelectableForm<String>>

    fun getSelected(groupName: GroupName): List<String>

    fun isItemSelected(groupName: GroupName, id: String) : Boolean

    fun switchAllSelection(groupName: GroupName)
    fun switchItemSelection(groupName: GroupName, item: String)


}