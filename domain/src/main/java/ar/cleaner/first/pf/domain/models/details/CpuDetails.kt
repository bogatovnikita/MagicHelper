package ar.cleaner.first.pf.domain.models.details

data class CpuDetails(
    val temperature: Int,
    val isOptimized: Boolean,
    val loadingIsDone: Boolean = false
)
