package yin_kio.files_and_apps_manager.presentation.start

import Yin_Koi.files_and_apps_manager.presentation.R

internal data class ScreenState(
    val percents: String = "",
    val occupiedAndTotal: String = "",
    val progress: Int = 0,
    val dangerText: String = "",
    val dangerColor: Int = 0,
    val dangerBg: Int = R.drawable.bg_button_danger
)