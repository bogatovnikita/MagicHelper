package file_manager.doman.overview.ui_out

data class GroupOut(
    val name: GroupName = GroupName.Images,
    val filesAndApps: List<String> = emptyList()
)