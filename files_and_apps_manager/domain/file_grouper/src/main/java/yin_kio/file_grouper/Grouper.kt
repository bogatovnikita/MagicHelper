package yin_kio.file_grouper

import file_manager.domain.server.FileOrApp
import file_manager.domain.server.GroupName
import file_manager.domain.server.selectable_form.SelectableForm

interface Grouper {

    fun groupFilesAndApps(files: List<FileOrApp>, apps: List<FileOrApp>) : Map<GroupName, SelectableForm<FileOrApp>>

}