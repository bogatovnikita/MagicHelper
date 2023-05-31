package ar.cleaner.second.pf.models

import ar.cleaner.first.pf.domain.models.Junk

data class Junks(
    val junkFilesList: List<JunkTypeFile>
) {
    data class JunkTypeFile(
        val id: String,
        val typeJunkFile: String,
        val memoryUsed: Long,
        val generalMemory: Double,
        val countJunkFiles: Int,
        val junkFilesList: List<Junk>,
        var isChecked: Boolean
    )
}