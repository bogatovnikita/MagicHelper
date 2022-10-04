package com.hedgehog.ar154.data

import com.hedgehog.ar154.R

enum class OptimizationTypes(val animationId: Int) {
    Boosting(R.raw.rocket),
    BatteryLow(R.raw.battery),
    BatteryMedium(R.raw.battery),
    BatteryHigh(R.raw.battery),
    Cpu(R.raw.temperature),
    Garbage(R.raw.trash),
    First(R.raw.search)
}