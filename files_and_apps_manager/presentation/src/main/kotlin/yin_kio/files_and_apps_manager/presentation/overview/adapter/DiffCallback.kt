package yin_kio.files_and_apps_manager.presentation.overview.adapter

import androidx.recyclerview.widget.DiffUtil
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem

internal class DiffCallback : DiffUtil.ItemCallback<FileOrAppItem>() {

    override fun areItemsTheSame(oldItem: FileOrAppItem, newItem: FileOrAppItem): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: FileOrAppItem, newItem: FileOrAppItem): Boolean {
        return oldItem == newItem
    }
}