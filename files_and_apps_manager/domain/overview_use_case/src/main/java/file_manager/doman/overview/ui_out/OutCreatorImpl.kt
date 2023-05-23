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
            selectedGroupContent = server.selectedGroupContent,
            selectedGroup = server.selectedGroup,
            isAllSelected = server.isAllSelected,
            selectedCount = server.selectedCount,
            sortingMode = server.sortingMode,
            availableGroups = server.groups.keys.toList()
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
            content = server.selectedGroupContent
        )
    }


    override fun createGroupSwitchingOut(): GroupSwitchingOut {
        return GroupSwitchingOut(
            groupName = server.selectedGroup,
            content = server.selectedGroupContent,
            selectionCount = server.selectedCount,
            isAllSelected = server.isAllSelected
        )
    }
}