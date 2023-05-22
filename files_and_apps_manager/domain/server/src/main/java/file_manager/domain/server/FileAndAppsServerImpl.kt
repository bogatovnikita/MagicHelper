package file_manager.domain.server

import file_manager.domain.server.selectable_form.SelectableForm

class FileAndAppsServerImpl : FileManagerServer {

    private var _sortingMode = SortingMode.BigFirst
    override val sortingMode: SortingMode get() = _sortingMode

    private val _content: MutableMap<GroupName, SelectableForm<FileOrApp>> = mutableMapOf()
    override var selectedGroup: GroupName = GroupName.Images
    override val hasSelected: Boolean get() = _content[selectedGroup]?.selected?.isNotEmpty()?:false
    override val isAllSelected: Boolean get() = _content[selectedGroup]?.isAllSelected?:false
    override val selectedCount: Int get() = _content[selectedGroup]?.selected?.size?:0
    override var groups: Map<GroupName, SelectableForm<FileOrApp>>
        get() = _content
        set(value) {
            value.forEach {
                _content[it.key] = it.value
            }
        }

    override val selectedGroupContent: List<FileOrApp>
        get() = _content[selectedGroup]?.content?: emptyList()

    override fun getSelected(groupName: GroupName): List<FileOrApp> {
        return _content[groupName]?.selected ?: emptyList()
    }

    override fun isItemSelected(groupName: GroupName, id: String): Boolean {
        _content[groupName]?.let {
            val item = it.content.find { it.id == id }?: return false
            return it.isItemSelected(item)
        }
        return false
    }

    override fun switchAllSelection(groupName: GroupName) {
        _content[groupName]?.switchAllSelection()
    }

    override fun switchItemSelection(groupName: GroupName, id: String) {
        _content[groupName]?.let {
            val item = it.content.find { it.id == id }?: return
            it.switchItemSelection(item)
        }
    }

    override fun setSortingMode(sortingMode: SortingMode) {
        _sortingMode = sortingMode

        when(sortingMode){
            SortingMode.NewFirst -> _content.forEach { it.value.sortBy { -it.lastTimeUsed } }
            SortingMode.OldFirst -> _content.forEach { it.value.sortBy { it.lastTimeUsed } }
            SortingMode.BigFirst -> _content.forEach { it.value.sortBy { -it.size } }
            SortingMode.SmallFirst -> _content.forEach { it.value.sortBy { it.size } }
        }
    }
}