package ar.cleaner.first.pf.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.data.OptimizationTypes
import ar.cleaner.first.pf.result.ResultFragment
import ar.cleaner.first.pf.ui.ads.preloadAd
import ar.cleaner.first.pf.ui.battery.BatteryProgressFragment
import ar.cleaner.first.pf.utils.MenuItems

object Actions {
    var onBack: () -> Unit = {}

    private val noNavigateUp = arrayOf(
        R.id.firstScanningFragment,
        R.id.cpuProgressFragment,
        R.id.boostProgressFragment,
        R.id.junkProgressFragment,
        R.id.batteryProgressFragment,
    )

    private val closeAppBackStack = arrayOf(
        R.id.menuFragment,
        R.id.splashFragment,
    )

    val goBackIfCan: Fragment.() -> Unit = {
        val navController = findNavController()
        val currentDestination = navController.currentDestination!!.id
        if (closeAppBackStack.contains(currentDestination)) {
            requireActivity().moveTaskToBack(false)
        } else if (!noNavigateUp.contains(currentDestination)) {
            navController.navigateUp()
            onBack()
        }
    }

    val goBack: Fragment.() -> Unit = {
        findNavController().navigateUp()
        onBack()
    }

    val goToFirstScanning: Fragment.() -> Unit = {
        findNavController().navigate(R.id.action_splashFragment_to_firstScanningFragment)
    }
    val goToMenu: Fragment.() -> Unit = {
        findNavController().navigate(R.id.action_firstScanningFragment_to_menuFragment)
    }
    val goToResult: Fragment.(
        menuItem: MenuItems,
        data: Any?,
        simpleData: String?
    ) -> Unit = { menuItem, data, simpleData ->
        findNavController().navigate(
            R.id.global_action_result,
            ResultFragment.getBundle(menuItem, data, simpleData)
        )
    }
    val goToCpu: Fragment.() -> Unit = {
        findNavController().navigate(R.id.global_action_cpu)
    }
    val goToCpuProgress: Fragment.() -> Unit = {
        preloadAd()
        findNavController().navigate(R.id.action_cpuFragment_to_cpuProgressFragment)
    }
    val goToBoost: Fragment.() -> Unit = {
        findNavController().navigate(R.id.global_action_boost)
    }
    val goToBoostProgress: Fragment.() -> Unit = {
        preloadAd()
        findNavController().navigate(R.id.action_boostFragment_to_boostProgressFragment)
    }
    val goToJunk: Fragment.() -> Unit = {
        findNavController().navigate(R.id.global_action_junk)
    }
    val goToJunkProgress: Fragment.() -> Unit = {
        preloadAd()
        findNavController().navigate(R.id.action_junkFragment_to_junkProgressFragment)
    }
    val goToBattery: Fragment.() -> Unit = {
        findNavController().navigate(R.id.global_action_battery)
    }
    val goToBatteryProgress: Fragment.(OptimizationTypes) -> Unit = {
        preloadAd()
        findNavController().navigate(R.id.action_batteryFragment_to_batteryProgressFragment,
            Bundle().apply {
                putString(BatteryProgressFragment.OPTIMIZATION_TYPE, it.name)
            }
        )
    }
}