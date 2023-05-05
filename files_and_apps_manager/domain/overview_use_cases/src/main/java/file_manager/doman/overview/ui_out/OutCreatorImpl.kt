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
            groups = server.groups.map {group ->
                GroupOut(
                    name = group.key,
                    ids = group.value
                )
            },
            isAllSelected = server.isAllSelected,
            selectedCount = server.selectedCount
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
}