package file_manager.domain.server.selectable_form

interface SelectableForm<T> {

    var content: List<T>
    val selected: List<T>
    val isAllSelected: Boolean

    fun switchItemSelection(item: T)
    fun switchAllSelection()

    fun isItemSelected(item: T) : Boolean

    fun <R : Comparable<R>> sortBy(selector: (T) -> R?) {
        content = content.sortedBy(selector)
    }

    fun clearSelected()

}