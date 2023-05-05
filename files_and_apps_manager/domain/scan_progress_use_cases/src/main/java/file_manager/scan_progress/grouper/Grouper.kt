package file_manager.scan_progress.grouper

import file_manager.domain.server.GroupName

interface Grouper {

    fun groupFilesAndApps(files: List<String>, apps: List<String>) : Map<GroupName, List<String>>

}