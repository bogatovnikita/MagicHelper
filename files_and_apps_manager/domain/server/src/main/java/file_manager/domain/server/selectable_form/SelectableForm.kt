package file_manager.domain.server.selectable_form

interface SelectableForm<T> {

    var content: Collection<T>
    val selected: Collection<T>
    val isAllSelected: Boolean

    fun switchItemSelection(item: T)
    fun switchAllSelection()

    fun isItemSelected(item: T) : Boolean

}