package ar.cleaner.first.pf.ui.menu

import android.view.MenuItem

data class MenuState(
    val menuItemList: List<MenuItem> = mutableListOf(),
    val batteryIsOptimize: Boolean = false,
    val boostIsOptimize: Boolean = false,
    val coolingIsOptimize: Boolean = false,
    val junkIsOptimize: Boolean = false
)