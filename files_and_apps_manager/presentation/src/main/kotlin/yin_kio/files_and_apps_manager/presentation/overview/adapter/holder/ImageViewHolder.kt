package yin_kio.files_and_apps_manager.presentation.overview.adapter.holder

import Yin_Koi.files_and_apps_manager.presentation.databinding.ListItemImageBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem

internal class ImageViewHolder private constructor(
    private val binding: ListItemImageBinding
) : ViewHolder(binding.root) {

    fun bind(item: FileOrAppItem){
        binding.name.text = item.name
        binding.description.text = item.description

        Glide.with(binding.image)
            .load(item.id)
            .into(binding.image)
    }

    companion object{
        fun from(parent: ViewGroup) : ImageViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemImageBinding.inflate(inflater, parent, false)
            return ImageViewHolder(binding)
        }
    }

}