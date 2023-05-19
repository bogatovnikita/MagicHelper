package yin_kio.files_and_apps_manager.presentation.overview.adapter.holder

import Yin_Koi.files_and_apps_manager.presentation.databinding.ListItemAppBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yin_kio.files_and_apps_manager.presentation.overview.getAppInfo
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem

internal class AppViewHolder(
    private val binding: ListItemAppBinding,
) :  ViewHolder(binding.root) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun bind(item: FileOrAppItem){
        binding.name.text = item.name
        val context = binding.root.context
        coroutineScope.launch(Dispatchers.IO) {
            // Возможно, это не самое лучшше решение, так как не контролируется отмена операции
            // однако оно пока сатбильно показывает себя с иконками приложения
            val icon = context.getAppInfo(item.id).loadIcon(context.packageManager)

            withContext(Dispatchers.Main){
                binding.image.setImageDrawable(icon)
            }
        }


    }


    companion object{
        fun from(parent: ViewGroup) : AppViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemAppBinding.inflate(inflater, parent, false)
            return AppViewHolder(binding)
        }
    }

}