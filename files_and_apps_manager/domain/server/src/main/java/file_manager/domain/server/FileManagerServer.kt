package file_manager.domain.server

interface FileManagerServer {

    val hasSelected: Boolean
    val isAllSelected: Boolean
    val selectedCount: Int

    var groups: Map<GroupName, List<String>>

    val selected: Map<GroupName, List<String>>

    fun isItemSelected(groupName: GroupName, id: String) : Boolean

    fun switchAllSelection(groupName: GroupName)
    fun switchItemSelection(groupName: GroupName, item: String)


}