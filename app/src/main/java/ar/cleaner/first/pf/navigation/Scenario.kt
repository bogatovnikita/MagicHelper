package ar.cleaner.first.pf.navigation

import androidx.fragment.app.Fragment
import ar.cleaner.first.pf.navigation.Actions.goBack
import ar.cleaner.first.pf.navigation.Actions.goToBattery
import ar.cleaner.first.pf.navigation.Actions.goToBoost
import ar.cleaner.first.pf.navigation.Actions.goToCpu
import ar.cleaner.first.pf.navigation.Actions.goToJunk
import ar.cleaner.first.pf.navigation.Actions.goToMenu
import ar.cleaner.first.pf.navigation.Actions.goToResult
import ar.cleaner.first.pf.utils.MenuItems

interface Scenario {
    val firstScanning: Fragment.() -> Unit
    val boostingProgress: Fragment.(MenuItems, Any?, String?) -> Unit
    val junkProgress: Fragment.(MenuItems, Any?, String?) -> Unit
    val batteryProgress: Fragment.(MenuItems, Any?, String?) -> Unit
    val cpuProgress: Fragment.(MenuItems, Any?, String?) -> Unit
}

object Scenario1 : Scenario {
    override val firstScanning: Fragment.() -> Unit = {
        goToMenu()
        goToBoost()
    }
    override val boostingProgress: Fragment.(MenuItems, Any?, String?) -> Unit ={ _, _, _->
        goToJunk()
    }
    override val junkProgress: Fragment.(MenuItems, Any?, String?) -> Unit = { _, _, _->
        goToBattery()
    }
    override val batteryProgress: Fragment.(MenuItems, Any?, String?) -> Unit = { _, _, _->
        goToCpu()
    }
    override val cpuProgress: Fragment.(MenuItems, Any?, String?) -> Unit = { _, _, _->
        goBack()
    }
}

object Scenario2 : Scenario {
    override val firstScanning: Fragment.() -> Unit = goToMenu
    override val boostingProgress: Fragment.(MenuItems, Any?, String?) -> Unit = goToResult
    override val junkProgress: Fragment.(MenuItems, Any?, String?) -> Unit = goToResult
    override val batteryProgress: Fragment.(MenuItems, Any?, String?) -> Unit = goToResult
    override val cpuProgress: Fragment.(MenuItems, Any?, String?) -> Unit = goToResult
}