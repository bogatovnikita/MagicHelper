package file_manager.domain.server

interface FileManagerServer {

    val hasSelected: Boolean
    val isAllSelected: Boolean
    val selectedCount: Int

    val groups: Map<GroupName, List<String>>

    val selected: List<String>

    fun isItemSelected(id: String) : Boolean

    fun setFilesAndApps(filesAndApps: List<String>)
    fun switchAllSelection()
    fun switchItemSelection(item: String)


}