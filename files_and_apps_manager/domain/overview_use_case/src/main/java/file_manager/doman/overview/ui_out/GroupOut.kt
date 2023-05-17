package file_manager.doman.overview.ui_out

import file_manager.domain.server.FileOrApp
import file_manager.domain.server.GroupName

data class GroupOut(
    val name: GroupName = GroupName.Images,
    val ids: List<FileOrApp> = emptyList()
)