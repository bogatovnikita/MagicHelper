package yin_kio.files_and_apps_manager.presentation.overview.models

import file_manager.domain.server.GroupName
import file_manager.domain.server.SortingMode

internal data class ScreenState(
    val selectedGroup: GroupName = GroupName.Audio,
    val availableGroups: List<GroupName> = emptyList(),
    val buttonText: String = "",
    val buttonAlpha: Float = 0.5f,
    val isShowSortingSelection: Boolean = false,
    val sortingMode: SortingMode = SortingMode.NewFirst,
    val sortingModeText: String = "",
    val content: List<FileOrAppItem> = emptyList(),
    val isAllSelected: Boolean = false
)