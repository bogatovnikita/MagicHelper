package com.hedgehog.ar154.utils

import com.hedgehog.ar154.AppClass
import com.hedgehog.ar154.R
import com.hedgehog.ar154.data.CacheInfo
import com.hedgehog.ar154.data.RamUsageModel

object OptimizationProvider {
    fun checkIsOptimized(menuItems: MenuItems): Boolean {
        return when (menuItems) {
            MenuItems.boost -> !NativeProvider.checkRamOverload(AppClass.instance)
            MenuItems.power -> !NativeProvider.checkBatteryDecrease(AppClass.instance)
            MenuItems.cooling -> NativeProvider.getOverheatedApps(AppClass.instance) == 0
            MenuItems.cleaning -> getGarbageSize() == 0
        }
    }

    fun getVarArgs(menuItems: MenuItems): Array<Any> {
        return when (menuItems) {
            MenuItems.boost -> getBoostVararg()
            MenuItems.power -> getPowerVararg()
            MenuItems.cooling -> getCoolingVararg()
            MenuItems.cleaning -> getCleaningVararg()
        }
    }

    private fun getCleaningVararg(): Array<Any> {
        return arrayOf<Any>(getGarbageSize())
    }

    private fun getCoolingVararg(): Array<Any> {
        return arrayOf<Any>(NativeProvider.getOverheatedApps(AppClass.instance))
    }

    fun getJunkItems(): List<CacheInfo> {

        val list = arrayListOf<CacheInfo>()
        val rows = AppClass.instance.resources.getStringArray(R.array.junk_items)
        val items = NativeProvider.getGarbageFilesCount(AppClass.instance)
        val sizes = NativeProvider.getGarbageSizeArray(AppClass.instance)
        for (i in 0 until rows.size) {
            list.add(CacheInfo(rows[i], sizes[i], items[i]))
        }
        return list
    }

    private fun getPowerVararg(): Array<Any> {
        val workingMinutes = NativeProvider.calculateWorkingMinutes(
            AppClass.instance,
            BatteryChargeReceiver.getInfo().value ?: 0
        )
        val hours = workingMinutes / 60
        val minutes = workingMinutes % 60
        return arrayOf<Any>(hours, minutes)
    }

    private fun getBoostVararg(): Array<Any> {
        return arrayOf<Any>(NativeProvider.getOverloadedPercents(AppClass.instance))
    }

    fun getRamUsageInfo(): RamUsageModel {
        return Utils.getUsageInfo()
    }

    fun getGarbageSize(): Int {
        val garbageItems = NativeProvider.getGarbageSizeArray(AppClass.instance)
        var size = 0
        garbageItems.forEach {
            size += it
        }
        return size
    }


}