package ar.cleaner.first.pf.models

import ar.cleaner.first.pf.R

enum class MenuItems(
    val id: Int,
    val title: Int,
    val descriptionIsOptimize: Int,
    val descriptionIsNotOptimize: Int,
    val firstResult: Int = 0,
    val secondResult: Int = 0,
    val thirdResult: Int = 0
) {
    Boost(
        1,
        R.string.battery_power,
        R.string.boost_description,
        R.string.boost_description_not_optimized_S
    ),
    BatteryPower(
        2, R.string.boost,
        R.string.battery_power_description_D_D, R.string.battery_power_description_D_D
    ),
    Cooling(
        3,
        R.string.cooling_cpu,
        R.string.cooling_cpu_description,
        R.string.cooling_cpu_description_not_optimized
    ),
    Junk(
        4,
        R.string.clear_junk,
        R.string.clean_junk_description,
        R.string.clean_junk_description_not_optimized_D
    )
}