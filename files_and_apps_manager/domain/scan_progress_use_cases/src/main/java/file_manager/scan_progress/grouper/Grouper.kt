package file_manager.scan_progress.grouper

import file_manager.domain.server.GroupName
import file_manager.domain.server.selectable_form.SelectableForm

interface Grouper {

    fun groupFilesAndApps(files: List<String>, apps: List<String>) : Map<GroupName, SelectableForm<String>>

}