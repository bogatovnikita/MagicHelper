package ar.cleaner.first.pf.models

import android.view.View
import ar.cleaner.first.pf.domain.models.BatteryMode

data class ModeGroup(
    val modeButton: View,
    val modeHeader: Int,
    val modeAction: List<String>,
    val mode: BatteryMode
)
