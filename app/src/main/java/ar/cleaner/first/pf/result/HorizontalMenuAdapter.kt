package ar.cleaner.first.pf.result

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.cleaner.first.pf.databinding.ItemMenuHorizontalBinding
import ar.cleaner.first.pf.utils.MenuItems

class HorizontalMenuAdapter(
    private val menuItems: List<MenuItems>,
    private val context: Context,
    private val onItemSelected: (MenuItems) -> Unit
) :
    RecyclerView.Adapter<HorizontalMenuAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMenuHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(menuItems[position])
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    inner class ViewHolder(private val binding: ItemMenuHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(menuItems: MenuItems) {
            binding.apply {
//                ivIcon.setImageResource(menuItems.headerIcon)
                tvTitle.text = context.getString(menuItems.title)
//                ivArrow.setImageResource(menuItems.arrow)
//                tvDescription.text = context.getString(menuItems.descriptionResult)
                root.setOnClickListener {
                    onItemSelected(menuItems)
                }
            }
        }

    }
}