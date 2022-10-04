package com.hedgehog.ar154.utils

import com.hedgehog.ar154.R

enum class MenuItems(
    val icon: Int,
    val title: Int,
    val description: Int,
    val description_not_optimized: Int,
    val arrow: Int,
    val headerIcon: Int,
    val descriptionResult: Int
) {
    boost(
        R.drawable.ic_boost,
        R.string.boost_title,
        R.string.boost_description,
        R.string.boost_description_not,
        R.drawable.arrow_boost,
        R.drawable.ic_boost_header,
        R.string.description_result_boost
    ),
    power(
        R.drawable.ic_power,
        R.string.power_title,
        R.string.power_description,
        R.string.power_description,
        R.drawable.arrow_power,
        R.drawable.ic_battery_header,
        R.string.description_result_power
    ),
    cooling(
        R.drawable.ic_cooling,
        R.string.cooling_title,
        R.string.cooling_description,
        R.string.cooling_description_not,
        R.drawable.arrow_cooling,
        R.drawable.ic_cooling_header,
        R.string.description_result_cpu
    ),
    cleaning(
        R.drawable.ic_clean,
        R.string.clean_title,
        R.string.clean_description,
        R.string.clean_description_not,
        R.drawable.arrow_clean,
        R.drawable.ic_clean_header,
        R.string.description_result_junk
    )
}