package com.hedgehog.ar154.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.hedgehog.ar154.navigation.Actions.goBackIfCan
import com.hedgehog.ar154.navigation.Actions.goToBattery
import com.hedgehog.ar154.navigation.Actions.goToBatteryProgress
import com.hedgehog.ar154.navigation.Actions.goToBoost
import com.hedgehog.ar154.navigation.Actions.goToBoostProgress
import com.hedgehog.ar154.navigation.Actions.goToCpu
import com.hedgehog.ar154.navigation.Actions.goToCpuProgress
import com.hedgehog.ar154.navigation.Actions.goToFirstScanning
import com.hedgehog.ar154.navigation.Actions.goToJunk
import com.hedgehog.ar154.navigation.Actions.goToJunkProgress
import com.hedgehog.ar154.result.ResultFragment
import com.hedgehog.ar154.ui.battery.BatteryFragment
import com.hedgehog.ar154.ui.battery.BatteryProgressFragment
import com.hedgehog.ar154.ui.boost.BoostFragment
import com.hedgehog.ar154.ui.boost.BoostProgressFragment
import com.hedgehog.ar154.ui.cpu.CpuFragment
import com.hedgehog.ar154.ui.cpu.CpuProgressFragment
import com.hedgehog.ar154.ui.first_scanning.FirstScanningFragment
import com.hedgehog.ar154.ui.junk.JunkFragment
import com.hedgehog.ar154.ui.junk.JunkProgressFragment
import com.hedgehog.ar154.ui.menu.MenuFragment
import com.hedgehog.ar154.ui.splash.SplashFragment

class NavigationFragmentFactory : FragmentFactory() {

    private var scenario: Scenario = Scenario1

    init {
        Actions.onBack = {scenario = Scenario2}
    }

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            BatteryFragment::class.java.name -> BatteryFragment(goToBatteryProgress)
            BatteryProgressFragment::class.java.name -> BatteryProgressFragment(scenario.batteryProgress)
            BoostFragment::class.java.name -> BoostFragment(goToBoostProgress)
            BoostProgressFragment::class.java.name -> BoostProgressFragment(scenario.boostingProgress)
            CpuFragment::class.java.name -> CpuFragment(goToCpuProgress)
            CpuProgressFragment::class.java.name -> CpuProgressFragment(scenario.cpuProgress)
            JunkFragment::class.java.name -> JunkFragment(goToJunkProgress)
            JunkProgressFragment::class.java.name -> JunkProgressFragment(scenario.junkProgress)
            SplashFragment::class.java.name -> SplashFragment(goToFirstScanning)
            FirstScanningFragment::class.java.name -> FirstScanningFragment(scenario.firstScanning)
            ResultFragment::class.java.name -> ResultFragment(
                onBack = goBackIfCan,
                onBattery = goToBattery,
                onBoost = goToBoost,
                onCpu = goToCpu,
                onJunk = goToJunk,
            )
            MenuFragment::class.java.name -> MenuFragment(
                onBattery = goToBattery,
                onBoost = goToBoost,
                onCpu = goToCpu,
                onJunk = goToJunk,
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}