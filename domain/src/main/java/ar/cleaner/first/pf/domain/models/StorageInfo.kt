package ar.cleaner.first.pf.domain.models

data class StorageInfo(
    val occupied: Double = 0.0,
    val free: Double = 0.0,
    val total: Double = 0.0,
    val usageMemoryPercents: Int = 0,
)