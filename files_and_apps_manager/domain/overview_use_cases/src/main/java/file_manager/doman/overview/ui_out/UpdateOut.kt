package file_manager.doman.overview.ui_out

data class UpdateOut(
    val isAllSelected: Boolean = false,
    val selectedCount: Int = 0,
    val groups: List<GroupOut> = emptyList()
)