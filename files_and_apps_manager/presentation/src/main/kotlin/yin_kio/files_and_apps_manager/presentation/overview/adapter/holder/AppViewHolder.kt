package yin_kio.files_and_apps_manager.presentation.overview.adapter.holder

import Yin_Koi.files_and_apps_manager.presentation.databinding.ListItemAppBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import file_manager.doman.overview.ui_out.Selectable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yin_kio.files_and_apps_manager.presentation.overview.adapter.SelectableImpl
import yin_kio.files_and_apps_manager.presentation.overview.getAppInfo
import yin_kio.files_and_apps_manager.presentation.overview.getAppInfoOrNull
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem

internal class AppViewHolder(
    private val binding: ListItemAppBinding,
    private val onUpdate: (fileOrApp: FileOrAppItem, selectable: Selectable) -> Unit,
    private val onItemClick: (fileOrApp: FileOrAppItem, selectable: Selectable) -> Unit
) :  ViewHolder(binding.root) {


    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val selectable = SelectableImpl(binding.checkbox)


    fun bind(item: FileOrAppItem){
        onUpdate(item, selectable)

        binding.name.text = item.name
        val context = binding.root.context

        coroutineScope.launch(Dispatchers.IO) {
            // Возможно, это не самое лучшше решение, так как не контролируется отмена операции
            // однако оно пока сатбильно показывает себя с иконками приложения
            val icon = context.getAppInfoOrNull(item.id)?.loadIcon(context.packageManager)

            withContext(Dispatchers.Main){
                binding.image.setImageDrawable(icon)
            }
        }


        binding.root.setOnClickListener { onItemClick(item, selectable) }
        binding.checkbox.setOnClickListener {onItemClick(item, selectable) }

    }


    companion object{
        fun from(parent: ViewGroup,
                 onUpdate: (fileOrApp: FileOrAppItem, selectable: Selectable) -> Unit,
                 onItemClick: (fileOrApp: FileOrAppItem, selectable: Selectable) -> Unit
        ) : AppViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemAppBinding.inflate(inflater, parent, false)
            return AppViewHolder(binding, onUpdate, onItemClick)
        }
    }

}