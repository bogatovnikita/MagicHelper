package file_manager.domain.server

interface FileManagerServer {

    val hasSelected: Boolean
    val isAllSelected: Boolean
    val selectedCount: Int

    var groups: Map<GroupName, List<String>>

    val selected: List<String>

    fun isItemSelected(id: String) : Boolean

    fun switchAllSelection()
    fun switchItemSelection(item: String)


}