package file_manager.doman.overview.ui_out

import file_manager.domain.server.SortingMode

data class UpdateOut(
    val isAllSelected: Boolean = false,
    val selectedCount: Int = 0,
    val groups: List<GroupOut> = emptyList(),
    val sortingMode: SortingMode = SortingMode.NewFirst
)