package yin_kio.files_and_apps_manager.presentation.overview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import yin_kio.files_and_apps_manager.presentation.overview.adapter.holder.AppViewHolder
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem

internal class AppAdapter : ListAdapter<FileOrAppItem, AppViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}