package com.hedgehog.ar154.navigation

import androidx.fragment.app.Fragment
import com.hedgehog.ar154.navigation.Actions.goBack
import com.hedgehog.ar154.navigation.Actions.goToBattery
import com.hedgehog.ar154.navigation.Actions.goToBoost
import com.hedgehog.ar154.navigation.Actions.goToCpu
import com.hedgehog.ar154.navigation.Actions.goToJunk
import com.hedgehog.ar154.navigation.Actions.goToMenu
import com.hedgehog.ar154.navigation.Actions.goToResult
import com.hedgehog.ar154.utils.MenuItems

interface Scenario {
    val firstScanning: Fragment.() -> Unit
    val boostingProgress: Fragment.(MenuItems, Any?, String?) -> Unit
    val junkProgress: Fragment.(MenuItems, Any?, String?) -> Unit
    val batteryProgress: Fragment.(MenuItems, Any?, String?) -> Unit
    val cpuProgress: Fragment.(MenuItems, Any?, String?) -> Unit
}

object Scenario1 : Scenario{
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

object Scenario2 : Scenario{
    override val firstScanning: Fragment.() -> Unit = goToMenu
    override val boostingProgress: Fragment.(MenuItems, Any?, String?) -> Unit = goToResult
    override val junkProgress: Fragment.(MenuItems, Any?, String?) -> Unit = goToResult
    override val batteryProgress: Fragment.(MenuItems, Any?, String?) -> Unit = goToResult
    override val cpuProgress: Fragment.(MenuItems, Any?, String?) -> Unit = goToResult
}