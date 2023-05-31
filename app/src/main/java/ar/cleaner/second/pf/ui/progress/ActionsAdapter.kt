package ar.cleaner.second.pf.ui.progress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.cleaner.second.pf.databinding.ItemActionBinding

class ActionsAdapter(actions: List<String>) :
    RecyclerView.Adapter<ActionsAdapter.ViewHolder>() {

    private val items = arrayListOf<String>()

    init {
        items.addAll(actions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemActionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setText(items[position], position)
    }

    override fun getItemCount() = items.size

    fun removeFirst() {
        items.removeFirst()
        notifyItemRemoved(0)
        notifyItemRangeChanged(0, 4)
    }

    class ViewHolder(private val binding: ItemActionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setText(text: String, position: Int) {
            val alpha = 1.0.toFloat() - (position.toFloat()) * 0.2.toFloat()
            binding.tvAction.apply {
                this.alpha = alpha
                this.text = text
            }
        }

    }
}
