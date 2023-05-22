package file_manager.domain.server

data class FileOrApp(
    val id: String = "",
    val size: Long = 0,
    val lastTimeUsed: Long = 0,
    val type: Type = Type.File,
) {
    enum class Type{
        File,
        App
    }
}