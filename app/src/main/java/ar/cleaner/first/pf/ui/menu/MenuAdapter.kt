package ar.cleaner.first.pf.ui.menu

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.ItemMenuBinding
import ar.cleaner.first.pf.utils.MenuItems
import ar.cleaner.first.pf.utils.OptimizationProvider

class MenuAdapter(
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

    override fun getItemCount() = MenuItems.values().size

    inner class ViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(menuItems: MenuItems) {
            binding.apply {
                iconIv.setImageResource(menuItems.icon)
                titleTv.text = binding.root.context.getString(menuItems.title)
            }
            if (optimizationProvider.checkIsOptimized(menuItems)) {
                val text = when (menuItems) {
                    MenuItems.BatteryPower -> binding.root.context.getString(
                        menuItems.description,
                        *OptimizationProvider.getVarArgs(menuItems)
                    )
                    else -> binding.root.context.getString(
                        menuItems.description
                    )
                }
                binding.descriptionTv.text = text
            } else {
                binding.apply {
                    descriptionTv.text = binding.root.context.getString(
                        menuItems.description_not_optimized,
                        *OptimizationProvider.getVarArgs(menuItems)
                    )
                    val redColorText = ContextCompat.getColor(binding.root.context, R.color.red)
                    descriptionTv.setTextColor(redColorText)
                }
            }

            binding.root.setOnClickListener {
                onItemSelected(menuItems)
            }
        }
    }
}