package yin_kio.files_and_apps_manager.presentation.overview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import yin_kio.files_and_apps_manager.presentation.overview.adapter.holder.ImageViewHolder
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem

internal class ImageAdapter : ListAdapter<FileOrAppItem, ImageViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}