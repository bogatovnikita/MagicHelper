package ar.cleaner.second.pf.ui.result

import ar.cleaner.second.pf.R
import ar.cleaner.second.pf.models.MenuHorizontalItems
import javax.inject.Inject

class ResultListProvider @Inject constructor() {
    fun getResultList(type: Int): List<MenuHorizontalItems> = listOf(
            MenuHorizontalItems(
                TYPE_BOOST,
                R.string.boost_title_name,
                R.drawable.ic_boost,
                R.string.speed_up_the_work_of_your_phone
            ),
            MenuHorizontalItems(
                TYPE_BATTERY,
                R.string.battery_title,
                R.drawable.ic_battery,
                R.string.extend_the_operation_of_your_phone
            ),
            MenuHorizontalItems(
                TYPE_TEMPERATURE,
                R.string.temperature_title,
                R.drawable.ic_cpu,
                R.string.temperature_need_check
            ),
            MenuHorizontalItems(
                TYPE_FILE_MANAGER,
                R.string.clear_junk,
                R.drawable.ic_junk,
                R.string.remove_the_trash
            )
    ).filter { item -> item.type != type }

    companion object {
        const val TYPE_BOOST = 2
        const val TYPE_BATTERY = 1
        const val TYPE_TEMPERATURE = 3
        const val TYPE_FILE_MANAGER = 4
    }

}