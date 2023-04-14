package file_manager.domain.server

interface FileManagerServer {

    fun setFilesAndApps(filesAndApps: List<String>)
    fun switchAllSelection()
    fun switchItemSelection(item: String)

}