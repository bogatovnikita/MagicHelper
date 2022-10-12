package ar.cleaner.first.pf.utils

import ar.cleaner.first.pf.AppClass
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.data.CacheInfo
import ar.cleaner.first.pf.data.RamUsageModel
import ar.cleaner.first.pf.data.StorageMemoryModel


object OptimizationProvider {
    fun checkIsOptimized(menuItems: MenuItems): Boolean {
        return when (menuItems) {
            MenuItems.Boost -> !NativeProvider.checkRamOverload(
                AppClass.instance
            )
            MenuItems.BatteryPower -> !NativeProvider.checkBatteryDecrease(AppClass.instance)
            MenuItems.CoolingCpu -> NativeProvider.getOverheatedApps(AppClass.instance) == 0
            MenuItems.CleaningJunk -> getGarbageSize() == 0
        }
    }

    fun getVarArgs(menuItems: MenuItems): Array<Any> {
        return when (menuItems) {
            MenuItems.Boost -> getBoostVararg()
            MenuItems.BatteryPower -> getPowerVararg()
            MenuItems.CoolingCpu -> getCoolingVararg()
            MenuItems.CleaningJunk -> getCleaningVararg()
        }
    }

    private fun getCleaningVararg(): Array<Any> {
        return arrayOf<Any>(getGarbageSize())
    }

    private fun getCoolingVararg(): Array<Any> {
        return arrayOf<Any>(
            NativeProvider.getOverheatedApps(
                AppClass.instance
            )
        )
    }

    fun getJunkItems(): List<CacheInfo> {

        val list = arrayListOf<CacheInfo>()
//        val rows = AppClass.instance.resources.getStringArray(R.array.junk_items)
        val items =
            NativeProvider.getGarbageFilesCount(AppClass.instance)
        val sizes =
            NativeProvider.getGarbageSizeArray(AppClass.instance)
//        for (i in 0 until rows.size) {
//            list.add(CacheInfo(rows[i], sizes[i], items[i]))
//        }
        return list
    }

    private fun getPowerVararg(): Array<Any> {
        val workingMinutes =
            NativeProvider.calculateWorkingMinutes(
                AppClass.instance,
                BatteryChargeReceiver.getInfo().value ?: 0
            )
        val hours = workingMinutes / 60
        val minutes = workingMinutes % 60
        return arrayOf<Any>(hours, minutes)
    }

    private fun getBoostVararg(): Array<Any> {
        return arrayOf<Any>(
            NativeProvider.getOverloadedPercents(
                AppClass.instance
            )
        )
    }

    fun getRamUsageInfo(): RamUsageModel {
        return Utils.getUsageInfo()
    }

    fun getMemoryStorage(): StorageMemoryModel {
        return Utils.getMemoryStorage()
    }

    fun getGarbageSize(): Int {
        val garbageItems =
            NativeProvider.getGarbageSizeArray(AppClass.instance)
        var size = 0
        garbageItems.forEach {
            size += it
        }
        return size
    }
}