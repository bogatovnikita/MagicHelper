package file_manager.domain.server.selectable_form

open class SimpleSelectableForm<T> : SelectableForm<T> {




    override var content: List<T> = mutableListOf()
        set(value) {
            _selected.clear()
            field = value
        }

    private val _selected: MutableList<T> = mutableListOf()
    override val selected: List<T> get() = _selected

    override val isAllSelected: Boolean get() {
        if (_selected.isEmpty()) return false
        return _selected.containsAll(content)
    }

    override fun switchItemSelection(item: T) {
        if (content.isEmpty()) return

        if (_selected.contains(item)){
            _selected.remove(item)
        } else {
            _selected.add(item)
        }
    }

    override fun switchAllSelection() {
        if (_selected.containsAll(content)){
            _selected.clear()
        } else {
            _selected.addAll(content)
        }
    }

    override fun isItemSelected(item: T): Boolean {
        return _selected.contains(item)
    }

    override fun clearSelected() {
        _selected.clear()
    }
}