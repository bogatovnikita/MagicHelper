package ar.cleaner.first.pf.domain.models

import java.util.*

sealed class Junk {
    abstract val name: String
    abstract val size: Double
    abstract val sizeReadable: String
    abstract val id: String
    abstract var isChecked: Boolean
    abstract val drawable: Any
}

data class JunkFile(
    val filePath: String,
    override val name: String,
    override val size: Double,
    override val sizeReadable: String,
    override val id: String = UUID.randomUUID().toString(),
    override var isChecked: Boolean = false,
    override var drawable: Any = "",
) : Junk()

data class App(
    val packageName: String,
    override val name: String,
    override val size: Double,
    override val sizeReadable: String,
    override val id: String = UUID.randomUUID().toString(),
    override var isChecked: Boolean = false,
    override var drawable: Any = "",
) : Junk()