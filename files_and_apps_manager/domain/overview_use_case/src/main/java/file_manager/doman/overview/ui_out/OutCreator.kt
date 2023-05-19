package file_manager.doman.overview.ui_out

interface OutCreator {

    fun createAllSelectionOut() : AllSelectionOut
    fun createUpdateOut() : UpdateOut
    fun createItemSelectionOut(itemId: String) : ItemSelectionOut
    fun createSortingModeOut() : SortingModeOut
    fun createGroupSwitchingOut() : GroupSwitchingOut

}