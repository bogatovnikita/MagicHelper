package com.hedgehog.ar154.ui.junk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hedgehog.ar154.R
import com.hedgehog.ar154.data.CacheInfo
import com.hedgehog.ar154.databinding.ItemCleanBinding

class JunkAdapter(private val items: List<CacheInfo>) :
    RecyclerView.Adapter<JunkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemCleanBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: JunkAdapter.ViewHolder, position: Int) {
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
                binding.tvInfo.text =
                    context.getString(R.string.d_elements, cacheInfo.count, cacheInfo.size)
            }

        }
    }
}