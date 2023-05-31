package ar.cleaner.second.pf.ui.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.cleaner.second.pf.databinding.ItemMenuHorizontalBinding
import ar.cleaner.second.pf.models.MenuHorizontalItems

class ResultAdapter(private val listener: Listener) :
    ListAdapter<MenuHorizontalItems, ResultAdapter.ResultViewHolder>(ItemCallback),
    View.OnClickListener {

    interface Listener {
        fun onChooseMenu(item: MenuHorizontalItems)
    }

    class ResultViewHolder(val binding: ItemMenuHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<MenuHorizontalItems>() {
        override fun areItemsTheSame(
            oldItem: MenuHorizontalItems,
            newItem: MenuHorizontalItems
        ): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(
            oldItem: MenuHorizontalItems,
            newItem: MenuHorizontalItems
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMenuHorizontalBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            root.tag = item
            titleTv.text = holder.binding.root.context.getString(item.title)
            descriptionTv.text = holder.binding.root.context.getString(item.description)
            iconIv.setImageResource(item.icon)
        }
    }

    override fun onClick(v: View) {
        val item = v.tag as MenuHorizontalItems
        listener.onChooseMenu(item)
    }

    companion object {
        const val BATTERY_KEY = 1
        const val BOOST_KEY = 2
        const val TEMPERATURE_KEY = 3
        const val CLEANING_KEY = 4
    }
}
