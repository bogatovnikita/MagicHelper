package com.hedgehog.ar154.data

enum class BatteryMode {

    low, medium, high;

    companion object {
        fun getByName(name: String?): BatteryMode {
            if (name.isNullOrEmpty()) {
                return low
            }
            return valueOf(name)
        }
    }

}