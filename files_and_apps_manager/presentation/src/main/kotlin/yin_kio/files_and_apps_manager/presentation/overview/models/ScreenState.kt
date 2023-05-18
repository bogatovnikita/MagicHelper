package yin_kio.files_and_apps_manager.presentation.overview.models

import file_manager.domain.server.GroupName
import file_manager.domain.server.SortingMode

internal data class ScreenState(
    val groupName: GroupName = GroupName.Audio,
    val buttonText: String = "",
    val buttonAlpha: Float = 0.5f,
    val isShowSortingSelection: Boolean = false,
    val sortingMode: SortingMode = SortingMode.NewFirst
)