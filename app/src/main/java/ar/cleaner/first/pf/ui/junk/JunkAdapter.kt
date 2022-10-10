package ar.cleaner.first.pf.ui.junk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.data.CacheInfo
import ar.cleaner.first.pf.databinding.ItemCleanBinding

class JunkAdapter(private val items: List<CacheInfo>) :
    RecyclerView.Adapter<JunkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemCleanBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val binding: ItemCleanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(cacheInfo: CacheInfo) {
            val context = binding.root.context
            binding.tvType.text = cacheInfo.item
            if(cacheInfo.size == 0) {
                binding.apply {
                    tvInfo.visibility = View.GONE
                    tvDone.visibility = View.VISIBLE
                }
            } else {
//                binding.tvInfo.text =
//                    context.getString(R.string.d_elements, cacheInfo.count, cacheInfo.size)
            }

        }
    }
}