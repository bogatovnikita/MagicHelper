package ar.cleaner.first.pf.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.ItemMenuBinding
import ar.cleaner.first.pf.models.MenuItems

class MenuAdapter(private val listener: Listener) :
    ListAdapter<MenuItems, MenuAdapter.MenuHolder>(ItemCallback),
    View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMenuBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return MenuHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            root.tag = item
            titleTv.text = item.title
            if (item.optimizeIsDone) {
                descriptionTv.text = item.descriptionIsOptimize
                descriptionTv.setTextColor(
                    ContextCompat.getColor(
                        holder.binding.root.context,
                        R.color.green
                    )
                )
            } else {
                descriptionTv.text = item.descriptionNotOptimize
                descriptionTv.setTextColor(
                    ContextCompat.getColor(
                        holder.binding.root.context,
                        R.color.red
                    )
                )
            }
            iconIv.setImageResource(item.icon)
        }
    }

    override fun onClick(v: View) {
        val item = v.tag as MenuItems
        listener.onChooseMenu(item)
    }

    class MenuHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onChooseMenu(item: MenuItems)
    }

    object ItemCallback : DiffUtil.ItemCallback<MenuItems>() {
        override fun areItemsTheSame(oldItem: MenuItems, newItem: MenuItems): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MenuItems, newItem: MenuItems): Boolean {
            return oldItem == newItem
        }
    }
}