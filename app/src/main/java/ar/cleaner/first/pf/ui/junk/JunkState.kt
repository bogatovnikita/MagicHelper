package ar.cleaner.first.pf.ui.junk

import ar.cleaner.first.pf.models.ParcelableJunk

data class JunkState(
    val valueEmptyFolders: Int = 0,
    val valueThumbnails: Int = 0,
    val valueUnnecessaryApk: Int = 0,
    val valueCache: Int = 0,
    val listParcelableJunk: List<ParcelableJunk> = emptyList(),
    val responseUseCase: Boolean = false,
    val isStorageGranted: Boolean = false,
    val isUsageStatsGranted: Boolean = false,
    val isOptimizeDone: Boolean = false
)