package ar.cleaner.first.pf.utils

import ar.cleaner.first.pf.R

enum class MenuItems(
    val icon: Int,
    val title: Int,
    val description: Int,
    val description_not_optimized: Int,
    val description_optimized_first: Int,
    val description_optimized_second: Int,
    val description_optimized_third: Int,
    val description_item_menu: Int
) {
    BatteryPower(
        R.drawable.ic_battery,
        R.string.battery_power,
        R.string.battery_power_description,
        R.string.battery_power_description,
        R.string.battery_power_optimized,
        R.string.working_time_increased_by_d,
        R.string.remaining_charging_time_d_h_d_min,
        R.string.extend_the_operation_of_your_phone
    ),
    Boost(
        R.drawable.ic_boost,
        R.string.boost,
        R.string.boost_description,
        R.string.boost_description_not_optimized,
        R.string.released_2f_gb,
        R.string.now_the_device_is_accelerated_by_d,
        R.string.available_memory_2f_gb_2f_gb,
        R.string.speed_up_the_work_of_your_phone
    ),
    CoolingCpu(
        R.drawable.ic_cpu,
        R.string.cooling_cpu,
        R.string.cooling_cpu_description,
        R.string.cooling_cpu_description_not_optimized,
        R.string.cooled_d,
        R.string.the_normal_temperature_of_the_processor_is_25_30,
        R.string.processor_temperature_d,
        R.string.cool_down_your_phone
    ),
    CleaningJunk(
        R.drawable.ic_junk,
        R.string.clear_junk,
        R.string.clean_junk_description,
        R.string.clean_junk_description_not_optimized,
        R.string.released_2f_gb,
        R.string.now_the_devices_memory_is_d_free,
        R.string.available_memory_2f_gb_2f_gb,
        R.string.remove_the_trash
    )
}