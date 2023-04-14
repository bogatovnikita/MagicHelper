package file_manager.doman.overview.ui_out

interface UiOuter {

    fun close()
    fun out(updateOut: UpdateOut)
    fun out(allSelectionOut: AllSelectionOut)
    fun showGroup(groupName: GroupName)

}