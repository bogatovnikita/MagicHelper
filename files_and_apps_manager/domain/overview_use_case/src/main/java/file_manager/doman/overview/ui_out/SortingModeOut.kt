package file_manager.doman.overview.ui_out

import file_manager.domain.server.FileOrApp
import file_manager.domain.server.SortingMode

data class SortingModeOut(
    val sortingMode: SortingMode = SortingMode.NewFirst,
    val content: List<FileOrApp> = emptyList()
)