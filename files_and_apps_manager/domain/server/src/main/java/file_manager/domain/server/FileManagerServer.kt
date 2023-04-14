package file_manager.domain.server

interface FileManagerServer {

    val hasSelected: Boolean

    fun setFilesAndApps(filesAndApps: List<String>)
    fun switchAllSelection()
    fun switchItemSelection(item: String)


}