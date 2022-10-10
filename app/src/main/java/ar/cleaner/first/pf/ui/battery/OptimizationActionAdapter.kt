package ar.cleaner.first.pf.ui.battery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.cleaner.first.pf.databinding.ItemOptimizationActionBinding

class OptimizationActionAdapter() :
    RecyclerView.Adapter<OptimizationActionAdapter.ViewHolder>() {

    private val items = mutableListOf<String>()

    fun setItems(modeActions: List<String>) {
        items.clear()
        items.addAll(modeActions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        ItemOptimizationActionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }
    override fun getItemCount() = items.size

    class ViewHolder(private val binding: ItemOptimizationActionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(s: String) {
            binding.tvAction.text = s
        }
    }
}