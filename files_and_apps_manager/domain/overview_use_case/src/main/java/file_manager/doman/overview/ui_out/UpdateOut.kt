package file_manager.doman.overview.ui_out

import file_manager.domain.server.FileOrApp
import file_manager.domain.server.GroupName
import file_manager.domain.server.SortingMode

data class UpdateOut(
    val isAllSelected: Boolean = false,
    val selectedCount: Int = 0,
    val selectedGroup: GroupName = GroupName.Images,
    val selectedGroupContent: List<FileOrApp> = emptyList(),
    val sortingMode: SortingMode = SortingMode.NewFirst,
    val availableGroups: List<GroupName> = emptyList()
)