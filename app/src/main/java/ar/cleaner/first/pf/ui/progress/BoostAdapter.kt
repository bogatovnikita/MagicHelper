package ar.cleaner.first.pf.ui.progress

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.cleaner.first.pf.databinding.ItemActionBinding
import ar.cleaner.first.pf.databinding.ItemPreviewActionBinding
import ar.cleaner.first.pf.domain.models.RunningApp
import com.bumptech.glide.Glide

class BoostAdapter(
    private val previewActions: List<String>,
    private val actions: List<RunningApp>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mergeList: MutableList<Any> = mutableListOf()

    init {
        mergeList()
    }

    private fun mergeList() {
        previewActions.forEach {
            mergeList.add(it)
        }
        actions.forEach {
            mergeList.add(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (mergeList[position]) {
            is String -> PREVIEW_ACTION_TYPE
            is RunningApp -> VIEW_ACTION_TYPE
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PREVIEW_ACTION_TYPE -> {
                PreviewViewHolder(
                    ItemPreviewActionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            VIEW_ACTION_TYPE -> {
                ActionViewHolder(
                    ItemActionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PreviewViewHolder -> {
                val item = mergeList[position] as String
                holder.bind(item, position)
            }
            is ActionViewHolder -> {
                val item = mergeList[position] as RunningApp
                holder.bind(item, position)
            }
        }
    }

    override fun getItemCount(): Int = mergeList.size

    inner class PreviewViewHolder(private val binding: ItemPreviewActionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            val alpha = 1.0f - (position.toFloat()) * 0.4f
            binding.tvAction.apply {
                this.text = item
                this.alpha = alpha
            }
        }
    }

    inner class ActionViewHolder(private val binding: ItemActionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RunningApp, position: Int) {
            val alpha = 1.0f - (position.toFloat()) * 0.4f

            binding.tvAction.apply {
                this.text = item.nameApp
                this.alpha = alpha
            }
            binding.imageView.apply {
                Glide.with(this)
                    .load(Uri.parse(item.uriIcon))
                    .into(this)

                this.alpha = alpha
            }
        }
    }

    fun removeFirst() {
        mergeList.removeFirst()
        notifyItemRemoved(0)
        notifyItemRangeChanged(0, mergeList.size)
    }

    companion object {
        const val PREVIEW_ACTION_TYPE = 1
        const val VIEW_ACTION_TYPE = 2
    }
}
