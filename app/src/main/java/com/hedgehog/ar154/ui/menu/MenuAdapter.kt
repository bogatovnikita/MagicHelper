package com.hedgehog.ar154.ui.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hedgehog.ar154.R
import com.hedgehog.ar154.databinding.ItemMenuBinding
import com.hedgehog.ar154.utils.MenuItems
import com.hedgehog.ar154.utils.OptimizationProvider

class MenuAdapter(
    private val context: Context,
    private val optimizationProvider: OptimizationProvider,
    private val onItemSelected: (MenuItems) -> Unit,
) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(MenuItems.values()[position])
    }

    override fun getItemCount(): Int {
        return MenuItems.values().size
    }

    inner class ViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(menuItems: MenuItems) {
            binding.apply {
                ivIcon.setImageResource(menuItems.icon)
                tvTitle.text = context.getString(menuItems.title)
                ivArrow.setImageResource(menuItems.arrow)
            }
            if (optimizationProvider.checkIsOptimized(menuItems)) {
                val text = when (menuItems) {
                    MenuItems.power -> context.getString(
                        menuItems.description,
                        *OptimizationProvider.getVarArgs(menuItems)
                    )
                    else -> context.getString(menuItems.description)
                }
                binding.tvDescription.text = text
            } else {
                binding.ivDot.visibility = View.VISIBLE
                binding.apply {
                    tvDescription.text = context.getString(
                        menuItems.description_not_optimized,
                        *OptimizationProvider.getVarArgs(menuItems)
                    )
                    val redColor = context.resources.getColor(R.color.color_red)
                    tvDescription.setTextColor(redColor)
                    tvTitle.setTextColor(redColor)
                }
            }
            binding.root.setOnClickListener {
                onItemSelected(menuItems)
            }
        }

    }
}