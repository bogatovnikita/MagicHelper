package file_manager.start_screen.ui_out

interface UiOuter {

    suspend fun close()
    suspend fun showScanProgress()
    fun out(usedMemOut: UpdateOut)

}