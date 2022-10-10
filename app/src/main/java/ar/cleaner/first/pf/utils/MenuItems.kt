package ar.cleaner.first.pf.utils

import ar.cleaner.first.pf.R

enum class MenuItems(
    val icon: Int,
    val title: Int,
    val description: Int,
    val description_not_optimized: Int
) {
    BatteryPower(
        R.drawable.ic_battery,
        R.string.battery_power,
        R.string.battery_power_description,
        R.string.battery_power_description,
    ),
    Boost(
        R.drawable.ic_boost,
        R.string.boost,
        R.string.boost_description,
        R.string.boost_description_not_optimized,
    ),
    CoolingCpu(
        R.drawable.ic_cpu,
        R.string.cooling_cpu,
        R.string.cooling_cpu_description,
        R.string.cooling_cpu_description_not_optimized
    ),
    CleaningJunk(
        R.drawable.ic_junk,
        R.string.clear_junk,
        R.string.clean_junk_description,
        R.string.clean_junk_description_not_optimized,
    )
}