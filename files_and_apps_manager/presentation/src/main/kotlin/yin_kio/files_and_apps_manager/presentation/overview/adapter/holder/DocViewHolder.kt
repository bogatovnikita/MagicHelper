package yin_kio.files_and_apps_manager.presentation.overview.adapter.holder

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.ListItemFileBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import file_manager.doman.overview.ui_out.Selectable
import yin_kio.files_and_apps_manager.presentation.overview.adapter.SelectableImpl
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem
import kotlin.io.path.Path
import kotlin.io.path.extension

internal class DocViewHolder private constructor(
    private val binding: ListItemFileBinding,
    private val onUpdate: (fileOrApp: FileOrAppItem, selectable: Selectable) -> Unit
) : ViewHolder(binding.root) {

    private val selectable = SelectableImpl(binding.checkbox)

    fun bind(item: FileOrAppItem){
        onUpdate(item, selectable)

        binding.name.text = item.name
        binding.description.text = item.description
        binding.icon.setImageResource(presentDocIconId(item.id))
    }

    private fun presentDocIconId(fileId: String) : Int{
        return when(Path(fileId).extension.lowercase()){
            "pdf" -> R.drawable.ic_pdf
            "doc" -> R.drawable.ic_doc
            "mp3" -> R.drawable.ic_mp3
            else -> R.drawable.ic_unknown
        }
    }

    companion object{
        fun from(parent: ViewGroup, onUpdate: (fileOrApp: FileOrAppItem, selectable: Selectable) -> Unit) : DocViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemFileBinding.inflate(inflater, parent, false)
            return DocViewHolder(binding, onUpdate)
        }
    }

}