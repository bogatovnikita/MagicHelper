package yin_kio.files_and_apps_manager.presentation.overview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import file_manager.doman.overview.ui_out.Selectable
import yin_kio.files_and_apps_manager.presentation.overview.adapter.holder.DocViewHolder
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem

internal class DocAdapter(
    private val onUpdate: (fileOrApp: FileOrAppItem, selectable: Selectable) -> Unit,
    private val onItemClick: (fileOrApp: FileOrAppItem, selectable: Selectable) -> Unit
) : ListAdapter<FileOrAppItem, DocViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocViewHolder {
        return DocViewHolder.from(parent, onUpdate, onItemClick)
    }

    override fun onBindViewHolder(holder: DocViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}