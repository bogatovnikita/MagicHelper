package yin_kio.files_and_apps_manager.presentation.overview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import file_manager.doman.overview.ui_out.Selectable
import yin_kio.files_and_apps_manager.presentation.overview.adapter.holder.AppViewHolder
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem

internal class AppAdapter(
    private val onUpdate: (fileOrApp: FileOrAppItem, selectable: Selectable) -> Unit
) : ListAdapter<FileOrAppItem, AppViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder.from(parent, onUpdate)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}