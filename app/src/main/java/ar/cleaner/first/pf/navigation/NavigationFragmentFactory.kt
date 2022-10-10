package ar.cleaner.first.pf.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import ar.cleaner.first.pf.navigation.Actions.goBackIfCan
import ar.cleaner.first.pf.navigation.Actions.goToBattery
import ar.cleaner.first.pf.navigation.Actions.goToBatteryProgress
import ar.cleaner.first.pf.navigation.Actions.goToBoost
import ar.cleaner.first.pf.navigation.Actions.goToBoostProgress
import ar.cleaner.first.pf.navigation.Actions.goToCpu
import ar.cleaner.first.pf.navigation.Actions.goToCpuProgress
import ar.cleaner.first.pf.navigation.Actions.goToFirstScanning
import ar.cleaner.first.pf.navigation.Actions.goToJunk
import ar.cleaner.first.pf.navigation.Actions.goToJunkProgress
import ar.cleaner.first.pf.result.ResultFragment
import ar.cleaner.first.pf.ui.battery.BatteryFragment
import ar.cleaner.first.pf.ui.battery.BatteryProgressFragment
import ar.cleaner.first.pf.ui.boost.BoostFragment
import ar.cleaner.first.pf.ui.boost.BoostProgressFragment
import ar.cleaner.first.pf.ui.cpu.CpuFragment
import ar.cleaner.first.pf.ui.cpu.CpuProgressFragment
import ar.cleaner.first.pf.ui.first_scanning.FirstScanningFragment
import ar.cleaner.first.pf.ui.junk.JunkFragment
import ar.cleaner.first.pf.ui.junk.JunkProgressFragment
import ar.cleaner.first.pf.ui.menu.MenuFragment
import ar.cleaner.first.pf.ui.splash.SplashFragment

class NavigationFragmentFactory : FragmentFactory() {

    private var scenario: Scenario = Scenario1

    init {
        Actions.onBack = {scenario = Scenario2 }
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