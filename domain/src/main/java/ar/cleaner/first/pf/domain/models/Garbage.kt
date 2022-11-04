package ar.cleaner.first.pf.domain.models

sealed class Garbage<T> {
    abstract val totalGarbageSize: Double
    abstract val list: List<T>
}

data class Cache(
    val totalGarbageSize: Long,
    val list: List<App>
)

data class EmptyFolders(
    override val totalGarbageSize: Double,
    override val list: List<JunkFile>
) : Garbage<JunkFile>()

data class UnnecessaryApk(
    override val totalGarbageSize: Double,
    override val list: List<JunkFile>
) : Garbage<JunkFile>()

data class Thumbnails(
    override val totalGarbageSize: Double,
    override val list: List<JunkFile>
) : Garbage<JunkFile>()
