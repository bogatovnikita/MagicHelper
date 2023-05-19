package file_manager.doman.overview.ui_out

import file_manager.domain.server.FileOrApp
import file_manager.domain.server.GroupName

data class GroupSwitchingOut(
    val groupName: GroupName = GroupName.Images,
    val content: List<FileOrApp> = emptyList()
)