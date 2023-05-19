package file_manager.doman.overview.ui_out

import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName

class OutCreatorImpl(
    private val server: FileManagerServer
) : OutCreator {

    override fun createAllSelectionOut(): AllSelectionOut {
        return AllSelectionOut(
            isAllSelected = server.isAllSelected,
            selectedCount = server.selectedCount
        )
    }

    override fun createUpdateOut(): UpdateOut {
        return UpdateOut(
            groups = createGroupOuts(),
            isAllSelected = server.isAllSelected,
            selectedCount = server.selectedCount,
            sortingMode = server.sortingMode
        )
    }


    override fun createItemSelectionOut(itemId: String): ItemSelectionOut {
        return ItemSelectionOut(
            id = itemId,
            isItemSelected = server.isItemSelected(GroupName.Video, itemId),
            isAllSelected = server.isAllSelected,
            selectedCount = server.selectedCount
        )
    }

    override fun createSortingModeOut(): SortingModeOut {
        return SortingModeOut(
            sortingMode = server.sortingMode,
            groups = createGroupOuts()
        )
    }

    private fun createGroupOuts() = server.groups.map { group ->
        GroupOut(
            name = group.key,
            ids = group.value.content
        )
    }

}