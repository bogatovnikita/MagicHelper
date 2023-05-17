package file_manager.domain.server

import file_manager.domain.server.selectable_form.SelectableForm

class FileAndAppsServerImpl : FileManagerServer {

    private val _selected: MutableMap<GroupName, SelectableForm<FileOrApp>> = mutableMapOf()
    private var currentGroup: GroupName = GroupName.Images

    override val hasSelected: Boolean get() = _selected[currentGroup]?.selected?.isNotEmpty()?:false
    override val isAllSelected: Boolean get() = _selected[currentGroup]?.isAllSelected?:false
    override val selectedCount: Int get() = _selected[currentGroup]?.selected?.size?:0
    override var groups: Map<GroupName, SelectableForm<FileOrApp>>
        get() = _selected
        set(value) {
            value.forEach {
                _selected[it.key] = it.value
            }
        }

    override fun getSelected(groupName: GroupName): List<FileOrApp> {
        return (_selected[groupName]?.content as List<FileOrApp>?)?: emptyList()
    }

    override fun isItemSelected(groupName: GroupName, id: String): Boolean {
        _selected[groupName]?.let {
            val item = it.content.find { it.id == id }?: return false
            return it.isItemSelected(item)
        }
        return false
    }

    override fun switchAllSelection(groupName: GroupName) {
        _selected[groupName]?.switchAllSelection()
    }

    override fun switchItemSelection(groupName: GroupName, id: String) {
        _selected[groupName]?.let {
            val item = it.content.find { it.id == id }?: return
            it.switchItemSelection(item)
        }
    }
}