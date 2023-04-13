package file_manager.start_screen

data class UpdateOut(
    val usedMemOut: UsedMemOut = UsedMemOut(),
    val isCleaned: Boolean = false
)