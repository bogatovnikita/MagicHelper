package ar.cleaner.first.pf.ui.result

import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.models.MenuHorizontalItems
import javax.inject.Inject

class ResultListProvider @Inject constructor() {

    fun getResultList(type: Int): List<MenuHorizontalItems> = listOf(
            MenuHorizontalItems(
                2,
                R.string.boost_title_name,
                R.drawable.ic_boost,
                R.string.speed_up_the_work_of_your_phone
            ),
            MenuHorizontalItems(
                1, R.string.battery_power,
                R.drawable.ic_battery,
                R.string.extend_the_operation_of_your_phone
            ),
            MenuHorizontalItems(
                3,
                R.string.temperature_title,
                R.drawable.ic_cpu,
                R.string.temperature_need_check
            ),
            MenuHorizontalItems(
                4,
                R.string.clear_junk,
                R.drawable.ic_junk,
                R.string.remove_the_trash
            )
    ).filter { item -> item.id != type }

    companion object {
        const val TYPE_BOOST = 3
        const val TYPE_BATTERY = 3
        const val TYPE_TEMPERATURE = 3
    }

}