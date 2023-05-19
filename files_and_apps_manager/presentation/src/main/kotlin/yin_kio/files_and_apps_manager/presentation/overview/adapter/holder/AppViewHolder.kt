package yin_kio.files_and_apps_manager.presentation.overview.adapter.holder

import Yin_Koi.files_and_apps_manager.presentation.databinding.ListItemAppBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import yin_kio.files_and_apps_manager.presentation.overview.getAppInfo
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem

internal class AppViewHolder(
    private val binding: ListItemAppBinding
) :  ViewHolder(binding.root) {

    fun bind(item: FileOrAppItem){
        binding.name.text = item.name
        val context = binding.root.context
        val icon = context.getAppInfo(item.id).loadIcon(context.packageManager)
        binding.image.setImageDrawable(icon)
    }


    companion object{
        fun from(parent: ViewGroup) : AppViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemAppBinding.inflate(inflater, parent, false)
            return AppViewHolder(binding)
        }
    }

}